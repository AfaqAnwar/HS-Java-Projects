package GamblingFX.Dependencies.Games;

import GamblingFX.Dependencies.Systems.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Crash Game
 * @Authors Afaq Anwar & Wai Hin Leung
 * @Version 02/11/2019
 */
public class Crash extends GamblingGame {
    // The current multiplier of the Game.
    private double currentMultiplier;
    // The multiplier at which the Game will end.
    private double bustingMultiplier;
    // HashMap that represents each player along with their multiplier.
    private HashMap<User, Double> currentPlayerMultipliers;
    private HashMap<User, Integer> currentPlayerBets;
    private boolean gameRunning;

    /**
     * Main Constructor.
     */
    public Crash() {
        this.currentMultiplier = 1.00;
        this.bustingMultiplier = generateRandomMultiplier();
        this.currentPlayerMultipliers = generateInitialPlayerMap(userManager.getCurrentActiveUsers());
        this.currentPlayerBets = generateInitialBetMap(userManager.getCurrentActiveUsers());
        this.gameRunning = false;
    }

    /**
     * Secondary Constructor
     * @param user Any User.
     */
    public Crash(User user) {
        this.addCurrentPlayer(user);
        this.currentMultiplier = 1.00;
        this.bustingMultiplier = generateRandomMultiplier();
        this.currentPlayerMultipliers = generateInitialPlayerMap(userManager.getCurrentActiveUsers());
        this.currentPlayerBets = generateInitialBetMap(userManager.getCurrentActiveUsers());
        this.gameRunning = false;
    }

    // Getters & Setters for some private fields.
    public double getCurrentMultiplier() { return this.currentMultiplier; }
    public void setCurrentMultiplier(double currentMultiplier) { this.currentMultiplier = currentMultiplier; }
    public double getBustingMultiplier() { return this.bustingMultiplier; }
    public boolean isGameRunning() {
        this.updateGameStatus();
        return this.gameRunning;
    }
    public Integer getPlayerBet(User user) { return this.currentPlayerBets.get(user); }
    public Double getPlayerMultiplier(User user) { return this.currentPlayerMultipliers.get(user); }

    /**
     * THE MOST COMPLEX ALGORITHM IN THE WORLD FOR GENERATING THE BUSTING MULTIPLIER. :P
     * This is essentially random inception.
     * @return A random value between 0 - 1000.
     */
    private double generateRandomMultiplier() {
        double oneMultiplierBustChance = Math.random() * 1;
        if (oneMultiplierBustChance < 0.02) {
            return 1.00;
        } else {
            double randomChance = Math.random() * 1;
            double bias = 1.00;
            if (randomChance <= 0.95) {
                bias = Math.random() * 0.05;
            }
            double range = Math.random() * 175;
            return ((Math.random() * range) * bias);
        }
    }

    /**
     * Populates the HashMap with initial data.
     * @param userList The ArrayList of playing Users.
     * @return HashMap that maps a User and their starting multiplier (0.0).
     */
    private HashMap<User, Double> generateInitialPlayerMap(ArrayList<User> userList) {
        HashMap<User, Double> playerMap = new HashMap<>();
        for (User currUser : userList) {
            playerMap.put(currUser, 0.00);
        }
        return playerMap;
    }

    /**
     * Populates the HashMap with initial data.
     * @param userList The ArrayList of playing Users.
     * @return HashMap that maps a User and an Integer.
     */
    private HashMap<User, Integer> generateInitialBetMap(ArrayList<User> userList) {
        HashMap<User, Integer> betMap = new HashMap<>();
        for (User currUser : userList) {
            betMap.put(currUser, 0);
        }
        return betMap;
    }

    /**
     * Updates all of the multipliers of the players that are currently playing.
     */
    public void updatePlayerMultipliers() {
        for (User currUser : this.userManager.getCurrentActiveUsers()) {
            if (userManager.getStatusOfUser(currUser).equals("Playing")) {
                if (currentPlayerMultipliers.containsKey(currUser)) {
                    currentPlayerMultipliers.replace(currUser, currentMultiplier);
                } else {
                    currentPlayerMultipliers.put(currUser, currentMultiplier);
                }
            }
        }
    }

    /**
     * Updates the User's bet amount.
     * @param bet Integer that represents the bet amount.
     * @param currUser Any User.
     */
    public void placeBet(int bet, User currUser){
        userManager.updatePlayerBalance(currUser, currUser.getBalance() - bet);
        if (currentPlayerBets.containsKey(currUser)) {
            currentPlayerBets.replace(currUser, bet);
        } else {
            currentPlayerBets.put(currUser, bet);
        }
    }

    /**
     * Makes sure the game is running while the current multiplier is less than the busting multiplier.
     */
    private void updateGameStatus() {
       gameRunning = this.currentMultiplier <= this.bustingMultiplier;
    }

    /**
     * Toggles the game status.
     */
    public void toggleGame() { this.gameRunning = !this.gameRunning; }

    /**
     * Updates each Player's (User's) balance if they have won the game.
     */
    public void updatePlayersBalance() {
        for (User user : currentPlayerBets.keySet()) {
            if (userManager.getStatusOfUser(user).equals("Won")) {
                userManager.updatePlayerBalance(user, user.getBalance() + currentPlayerBets.get(user) + (int) Math.floor(currentPlayerBets.get(user) * currentPlayerMultipliers.get(user)));
            }
        }
    }

    /**
     * If the game ended and the users have not withdrawn their bet, they have lost the game.
     */
    public void setLosingPlayers() {
        for (User user : userManager.getStatusOfUsers().keySet()) {
            if (userManager.getStatusOfUser(user).equals("Playing") && !gameRunning) {
                userManager.setStatusOfUser(user, "Lost");
            }
        }
    }
}