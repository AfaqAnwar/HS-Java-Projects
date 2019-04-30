package GamblingFX.Controllers;

import GamblingFX.Dependencies.Games.Roulette;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class RouletteController {
    public Pane pane;
    public Label user_name;
    public Label user_balance;
    public Label message1;
    public JFXTextField bet_amount;
    public JFXButton lock_in;
    public JFXButton clear;

    public Label message2;
    public Label message3;
    public JFXButton red;
    public JFXButton black;
    public JFXButton green;

    public Label message4;
    public StackPane stackPane;

    public String color;

    public int multiplier;
    public int bet;
    public int potential;

    public JFXButton play;

    public Rectangle box1;
    public Rectangle box2;
    public Rectangle box3;
    public Rectangle box4;
    public Rectangle box5;
    public Rectangle marker;

    public ArrayList<Rectangle> boxes = new ArrayList<>();
    int boxIndex;
    int wheelIndex;
    int tick;
    String box1id;

    int delay;

    private Roulette rgame;
    public void initialize(){
        rgame = new Roulette(LoginController.currentUser);
        user_name.setText("Welcome, " + LoginController.currentUser.getFirstName() + ".");
        user_balance.setText("You currently have " + LoginController.currentUser.getBalance() + " coin(s).");
        message1.setText("How much would you like to bet?");

        message2.setText("Please choose a color.");

        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        boxes.add(box5);

        for(int i=0;i<boxes.size();i++){
            boxes.get(i).setVisible(false);
        }
        marker.setVisible(false);
    }

    public void red(ActionEvent actionEvent){
        color = "red";
        multiplier = 2;
        message3.setText("You have selected red. The multiplier is " + multiplier + ".");
        updatePotential();
    }

    public void black(ActionEvent actionEvent){
        color = "black";
        multiplier = 2;
        message3.setText("You have selected black. The multiplier is " + multiplier + ".");
        updatePotential();
    }

    public void green(ActionEvent actionEvent){
        color = "green";
        multiplier = 14;
        message3.setText("You have selected green. The multiplier is " + multiplier + ".");
        updatePotential();
    }

    public void lock(ActionEvent actionEvent){
        if(validateAmount()){
            bet = Integer.parseInt(bet_amount.getText());
            stackPane.getChildren().clear();
            message1.setText("You have locked in " + bet + " coin(s).");
            updatePotential();
        } else {
            this.displayAlert();
        }
    }

    public void clear(ActionEvent actionEvent){
        bet_amount.clear();
    }

    public void updatePotential(){
        if(multiplier!=0&&bet!=0){
            potential = bet * multiplier;
            message4.setText("You can potentially win " + potential + " coin(s).");
            play.setVisible(true);
        }
    }

    public void play(){
        play.setVisible(false);
        lock_in.setVisible(false);
        clear.setVisible(false);
        black.setVisible(false);
        red.setVisible(false);
        green.setVisible(false);
        bet_amount.setVisible(false);
        rgame.placeBet(bet,LoginController.currentUser);
        user_balance.setText("You currently have " + LoginController.currentUser.getBalance() + " coin(s).");
        runGame();
    }

    public void runGame(){
        ArrayList<String> wheel = Roulette.generateWheel();
        tick = (int)(Math.random()*101);
        boxIndex = -1;
        wheelIndex = -1;
        delay = 0;


        for(int i=0;i<5;i++){
            boxIndex++;
            wheelIndex++;
            if(wheel.get(wheelIndex).equals("black")) {
                boxes.get(boxIndex).setFill(Color.BLACK);
            } else if (wheel.get(wheelIndex).equals("red")){
                boxes.get(boxIndex).setFill(Color.RED);
            } else {
                boxes.get(boxIndex).setFill(Color.GREEN);
            }
        }

        for(int i=0;i<boxes.size();i++){
            boxes.get(i).setVisible(true);
        }
        marker.setVisible(true);

        boxIndex = 0;

        AnimationTimer game = new AnimationTimer() {

            public void handle(long now) {
                for(int i=0;i<5;i++){
                    if(i==0){
                        if(wheel.get(wheelIndex).equals("black")){
                            box1id = "black";
                        } else if(wheel.get(wheelIndex).equals("red")){
                            box1id = "red";
                        } else {
                            box1id = "green";
                        }
                    }

                    if(wheel.get(wheelIndex).equals("black")){
                        boxes.get(i).setFill(Color.BLACK);
                    } else if(wheel.get(wheelIndex).equals("red")){
                        boxes.get(i).setFill(Color.RED);
                    } else {
                        boxes.get(i).setFill(Color.GREEN);
                    }

                    wheelIndex++;
                    if(wheelIndex>15){
                        wheelIndex = 0;
                    }
                }

                while(delay<1000000000){
                    delay++;
                }
                delay = 0;

                tick++;
                if(tick==100){
                    checkResult(box1id);
                    this.stop();
                }
            }
        };
        game.start();
    }

    public void checkResult(String color){
        play.setVisible(true);
        lock_in.setVisible(true);
        clear.setVisible(true);
        black.setVisible(true);
        red.setVisible(true);
        green.setVisible(true);
        bet_amount.setVisible(true);

        String text = "You picked " + this.color +" and it landed on " + color;
        if (this.color.equals(color)){
            rgame.updateWinnerBalance(LoginController.currentUser, potential);
            text += ". You won " + potential + " coin(s).";
        } else {
            text += ". You lost " + bet + " coin(s).";
        }
        message4.setText(text);
        user_balance.setText("You currently have " + LoginController.currentUser.getBalance() + " coin(s).");
    }
    /**
     * Makes sure the betting amount entered is a whole number that is less than 1,000,000,000.
     * @return True if it meets the requirements, false otherwise.
     */
    private boolean validateAmount() {
        return (bet_amount.getText().length() < 10 && bet_amount.getText().length() > 0 && Integer.parseInt(bet_amount.getText())<=LoginController.currentUser.getBalance());
    }

    /**
     * Displays alerts based on the user error.
     */
    private void displayAlert() {
        if (Integer.parseInt(bet_amount.getText())>LoginController.currentUser.getBalance()) {
            JFXDialog dialog = new JFXDialog();
            dialog.setContent(new Label("Insufficient funds!"));
            dialog.show(stackPane);
        } else if (bet_amount.getText().length() > 10) {
            JFXDialog dialog = new JFXDialog();
            dialog.setContent(new Label("Please place a bet over from 0 to 1,000,000,000!"));
            dialog.show(stackPane);
        }
    }
}
