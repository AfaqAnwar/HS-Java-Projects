package GamblingFX.Dependencies.Systems;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User Manager.
 * @Author Afaq Anwar
 * @Version 02/06/2019
 */
public class UserManager {
    // ArrayList containing all active Users.
    private ArrayList<User> currentActiveUsers;
    // HashMap that maps each User ID with the status of the User.
    private HashMap<User, String> statusOfUsers;

    /**
     * Main Constructor.
     */
    public UserManager() {
        currentActiveUsers = new ArrayList<>();
        statusOfUsers = new HashMap<>();
    }

    // Getters for each Private Field.
    public ArrayList<User> getCurrentActiveUsers() { return this.currentActiveUsers; }
    public HashMap<User, String> getStatusOfUsers() { return this.statusOfUsers; }


    /**
     * Add a User to the current ArrayList of active Users.
     * @param user Any User.
     */
    public void addActiveUser(User user) { currentActiveUsers.add(user); }

    /**
     * Remove any User from the ArrayList of Active Users.
     * @param user Any User.
     */
    public void removeActiveUser(User user) { currentActiveUsers.remove(user); }

    /**
     * Get the status of a user.
     * @param user User that needs to be queried.
     * @return String that represents the status of the User if the User exists, "NULL" otherwise.
     */
    public String getStatusOfUser(User user) {
        if (statusOfUsers.containsKey(user)) {
            return statusOfUsers.get(user);
        } else {
            return "NULL";
        }
    }

    /**
     * Updates the HashMap with status of User.
     * @param user Any User.
     * @param status String that represents the current User status.
     */
    public void setStatusOfUser(User user, String status) {
        if (!statusOfUsers.containsKey(user)) {
            statusOfUsers.put(user, status);
        } else {
            statusOfUsers.replace(user, status);
        }
    }

    /**
     * Validates the bet amount to make sure the User has enough balance.
     * @param user Any User.
     * @param amount The Integer representing the User's bet amount.
     * @return True if the User has enough funds, false otherwise.
     */
    public boolean validateAmount(User user, int amount) { return user.getBalance() >= amount; }

    /**
     * Changes the User balance.
     * @param user Any User.
     * @param amount Integer that represents the amount
     */
    public void updatePlayerBalance(User user, int amount) { user.setBalance(amount); }
}