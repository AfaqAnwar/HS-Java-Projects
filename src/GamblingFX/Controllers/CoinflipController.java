package GamblingFX.Controllers;

import GamblingFX.Dependencies.Games.Coinflip;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ResourceBundle;

public class CoinflipController implements Initializable {

    @FXML private TextField amount;
    @FXML private Button h;
    @FXML private Button t;
    @FXML private Arc red;
    @FXML private Arc black;
    @FXML private  Label prompt;
    @FXML private Label balance;
    private double timer = 2000;
    private double rotateSpeed;
    private Coinflip cf;

    public boolean validate()
    {
        return (amount.getText().matches("[0-9]") && amount.getText().length() < 10 && LoginController.currentUser.getBalance() >Integer.parseInt(amount.getText()));
    }

    public void displayAlert() {
        if (amount.getText().matches("[^0-9]")) {
            prompt.setText("Bets are whole numbers only.");

        } else if (amount.getText().length() > 10) {
            prompt.setText("Please bet a smaller amount. (Less than 1 Billion.)");

        }
    }
    public void displayAlert(boolean amnt, int b){
        if(amnt) {
            prompt.setText("You've won " + 2 * b);
        }
        else{
            prompt.setText("You've lost " + b);
        }
    }

    public void rotate(double rotateAmnt) {
        h.setDisable(true);
        t.setDisable(true);
        timer = 100;
        rotateSpeed = rotateAmnt;
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(timer > 0)
                {
                    timer -= 1;
                    red.getTransforms().add(new Rotate(rotateSpeed/100));
                    black.getTransforms().add(new Rotate(rotateSpeed/100));
                }
                else
                {
                    h.setDisable(false);
                    t.setDisable(false);
                    this.stop();
                }
            }
        }.start();
    }

    public void handler(javafx.event.ActionEvent e) {
        if(!validate())
        {
            displayAlert();
        }

        cf.heads =true;
        double x = cf.bet(Integer.parseInt(amount.getText()),LoginController.currentUser);

        rotate(x);
        displayAlert(cf.win,Integer.parseInt(amount.getText()));
        balance.setText(Integer.toString(LoginController.currentUser.getBalance()));

    }
    public void handlert(javafx.event.ActionEvent e) {
        if(!validate())
        {
            displayAlert();
        }

        cf.heads =false;
        double x = cf.bet(Integer.parseInt(amount.getText()),LoginController.currentUser);
        rotate(x);
        displayAlert(cf.win,Integer.parseInt(amount.getText()));
        balance.setText(Integer.toString(LoginController.currentUser.getBalance()));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cf = new Coinflip();
        cf.addCurrentPlayer(LoginController.currentUser);
        balance.setText(Integer.toString(LoginController.currentUser.getBalance()));
    }
}
