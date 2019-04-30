package GamblingFX.Dependencies.Games;

import GamblingFX.Controllers.LoginController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import GamblingFX.Dependencies.Systems.User;
import GamblingFX.Dependencies.Systems.UserManager;

/**
 * Jack Hoang and Kent Li
 */
public class Jackpot extends GamblingGame
{
    private int betTotal = 0;

    private ArrayList<String> playerPool = new ArrayList<>();

    private ArrayList<String> playerNames = new ArrayList<>();

    private ArrayList<String> array3 = new ArrayList<>();

    private ArrayList<String> bettingArray = new ArrayList<>();

    private ArrayList<String> winPercent = new ArrayList<>();

    private ArrayList<String> playerPercent = new ArrayList<>();

    private String win = "";

    private int sum = 0;

    private double win0;

    private double win1;

    private double win2;

    private double win3;

    private double win4;

    private double wini;

    private int winDex;

    public Jackpot()
    {
        this.win0 = win0;
        this.win1 = win1;
        this.win2 = win2;
        this.win3 = win3;
        this.win4 = win4;
        this.wini = wini;
    }

    //Fills the names, bets and percentages of the bots
    public ArrayList<String> fillPlayerNames()
    {
        playerNames.add(0,"Bot1");
        playerNames.add(1,"Bot2");
        playerNames.add(2,"Bot3");
        playerNames.add(3,"Bot4");
        playerNames.add(4,"Bot5");

        return playerNames;
    }

    public ArrayList<String> fillPlayerPool()
    {
        Random rand = new Random();
        for(int i = 0; i < 5; i++)
        {
            playerPool.add(Integer.toString((rand.nextInt(1000))));
        }
        array3.add(playerNames.get(0) + ", " + playerPool.get(0));
        array3.add(playerNames.get(1) + ", " + playerPool.get(1));
        array3.add(playerNames.get(2) + ", " + playerPool.get(2));
        array3.add(playerNames.get(3) + ", " + playerPool.get(3));
        array3.add(playerNames.get(4) + ", " + playerPool.get(4));
        return array3;
    }

    public int findPlayerPoolSum()
    {
        for(int i = 0; i < playerPool.size(); i++)
        {
            sum+=Integer.parseInt(playerPool.get(i));
        }
        return sum;
    }

    //The add functions make it so that you can do jp.addPlayerName(the current user in the final game)
    public ArrayList<String> addPlayerName(int i, String name)
    {
        playerNames.add(i,name);
        return playerNames;
    }

    public ArrayList<String> addPlayerBet(int i, String bet)
    {
        playerPool.add(i,bet);
        array3.add(playerNames.get(i) + ", " + playerPool.get(i));
        return array3;
    }

    //Calculates win percents for everyone
    public ArrayList<String> addPlayerPercent(int i)
    {
        findPlayerPoolSum();
        win0 = (Double.valueOf(playerPool.get(0))/sum) * 100;
        win1 = (Double.valueOf(playerPool.get(1))/sum) * 100;
        win2 = (Double.valueOf(playerPool.get(2))/sum) * 100;
        win3 = (Double.valueOf(playerPool.get(3))/sum) * 100;
        win4 = (Double.valueOf(playerPool.get(4))/sum) * 100;
        wini = (Double.valueOf(playerPool.get(i))/sum) * 100;
        winPercent.add(array3.get(0) + ", " + win0);
        winPercent.add(array3.get(1) + ", " + win1);
        winPercent.add(array3.get(2) + ", " + win2);
        winPercent.add(array3.get(3) + ", " + win3);
        winPercent.add(array3.get(4) + ", " + win4);
        winPercent.add(array3.get(i) + ", " + wini);
        return winPercent;
    }

    public String returnWinnerPercent()
    {
        playerPercent.add(Double.toString(win0));
        playerPercent.add(Double.toString(win1));
        playerPercent.add(Double.toString(win2));
        playerPercent.add(Double.toString(win3));
        playerPercent.add(Double.toString(win4));
        playerPercent.add(Double.toString(wini));
        return playerPercent.get(winDex);
    }

    //Making a separate arraylist for the actual betting, more bet = more chance to win
    public ArrayList<String> addToBettingArr(int j)
    {
        for(int i = 0; i < Integer.parseInt(playerPool.get(0)); i++)
        {
            bettingArray.add(playerNames.get(0));
        }
        for(int i = 0; i < Integer.parseInt(playerPool.get(1)); i++)
        {
            bettingArray.add(playerNames.get(1));
        }
        for(int i = 0; i < Integer.parseInt(playerPool.get(2)); i++)
        {
            bettingArray.add(playerNames.get(2));
        }
        for(int i = 0; i < Integer.parseInt(playerPool.get(3)); i++)
        {
            bettingArray.add(playerNames.get(3));
        }
        for(int i = 0; i < Integer.parseInt(playerPool.get(4)); i++)
        {
            bettingArray.add(playerNames.get(4));
        }
        for(int i = 0; i < Integer.parseInt(playerPool.get(j)); i++)
        {
            bettingArray.add(playerNames.get(j));
        }
        Collections.shuffle(bettingArray);
        return bettingArray;
    }

    //The actual picking of the winner by picking a random index in the betting array
    public String pickWinner()
    {
        Random k = new Random();
        int winningIndex = k.nextInt(bettingArray.size());
        win = bettingArray.get(winningIndex);
        for(int i = 0; i < playerNames.size(); i++)
        {
            if(win.equals(playerNames.get(i)))
            {
                winDex = i;
            }
        }
        return win;
    }

    //These 3 funcs below return a specific name, bet, and percent depending on the index.

    public String returnNames(int i)
    {
        return playerNames.get(i);
    }

    public String returnBets(int i)
    {
        return playerPool.get(i);
    }

    public String returnPercent(int i)
    {
        return playerPercent.get(i);
    }
}
