package GamblingFX.Dependencies.Games;

import GamblingFX.Dependencies.Systems.User;

public class Coinflip extends GamblingGame{

    public boolean heads = false;
    public boolean win = false;
    //red = head = true
    //black = tails =false
    private double greenPos = 0;

    public double bet(int b,User u)
    {
        win = false;
        double rotateAmnt = 360*Math.random();
        userManager.updatePlayerBalance(u,u.getBalance() + -b);
        greenPos += rotateAmnt;
        if(greenPos >360)
        {
            greenPos -=360;
        }
        if(heads && greenPos>180)
        {
            userManager.updatePlayerBalance(u,u.getBalance() + 2*b);
            win = true;

        }
        else if(!heads && greenPos<=180)
        {
            userManager.updatePlayerBalance(u,u.getBalance() + 2*b);
            win = true;

        }
        else {
            win = false;
        }
        return rotateAmnt;
    }


    public double getGreenPos()
    {
        return greenPos;
    }
    public void setGreenPos(double x)
    {
        greenPos = x;
    }
}
