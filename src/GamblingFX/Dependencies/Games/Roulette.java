package GamblingFX.Dependencies.Games;

import GamblingFX.Dependencies.Systems.User;

import java.util.ArrayList;

/**
 * Roulette game
 * @Authors Diego Cordova & Nick Chen
 */


//change place bet there is no pot just multiplies your bet if you win or just get nothing

public class Roulette extends GamblingGame {

    private User player;

    public Roulette(User user){
        this.player = user;
    }
    public void placeBet(int betAmount, User player){
        userManager.updatePlayerBalance(player, player.getBalance()-(betAmount));
    }

    public static ArrayList<String> generateWheel(){
        ArrayList<String> wheel = new ArrayList<>();
        wheel.add("green");
        for(int i = 1;i<16;i++){
            if(i%2==0) {
                wheel.add("black");
            }else {
                wheel.add("red");
            }
        }
        return wheel;
    }

    public void updateWinnerBalance(User player, int winAMount){
            userManager.updatePlayerBalance(player, player.getBalance()+winAMount);
    }




}
