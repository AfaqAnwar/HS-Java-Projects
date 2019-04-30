package SimonSays.Controllers;

import SimonSays.Dependencies.SimonSays;
import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller linked to GameUI.fxml
 * @Author Afaq Anwar
 * @Version 01/05/2019
 */
public class GameController {
    // This AnchorPane (1px * 1px) is only generated to allow seamless Scene switching.
    @FXML private AnchorPane anchorPane;
    @FXML private Arc greenArc;
    @FXML private Arc redArc;
    @FXML private Arc yellowArc;
    @FXML private Arc blueArc;
    @FXML private Label scoreText;

    private static int score;
    private SimonSays simonSays = new SimonSays();
    private AnimationTimer gameCheck;

    public static int getScore() { return score; }

    /**
     * Plays the initial pattern using an Animation Timer.
     * This timer is stopped within itself in order to allow user input.
     */
    public void initialize() {
       gameCheck = new AnimationTimer() {
           @Override
           public void handle(long now) {
               if (simonSays.getCurrentIndex() == 0) {
                   scoreText.setText(Integer.toString(simonSays.getScore()));
                   score = simonSays.getScore();
                   playPattern();
                   this.stop();
               }
           }
       };
       startGameTimer();
    }

    /**
     * Starts the gameCheck Animation Timer.
     */
    private void startGameTimer() { gameCheck.start(); }

    /**
     * Handles input for the Green Arc.
     */
    @FXML
    private void selectGreen() {
        if (!simonSays.checkInput("Green")) {
            endGame();
        } else {
            animateTile(greenArc);
        }
    }

    /**
     * Handles input for the Red Arc.
     */
    @FXML
    private void selectRed() {
        if (!simonSays.checkInput("Red")) {
            endGame();
        } else {
            animateTile(redArc);
        }
    }

    /**
     * Handles input for the Yellow Arc.
     */
    @FXML
    private void selectYellow() {
        if (!simonSays.checkInput("Yellow")) {
            endGame();
        } else {
            animateTile(yellowArc);
        }
    }

    /**
     * Handles the input for the Blue Arc.
     */
    @FXML
    private void selectBlue() {
        if (!simonSays.checkInput("Blue")) {
            endGame();
        } else {
            animateTile(blueArc);
        }
    }

    /**
     * Changes the fill of an Arc to create a light up effect.
     * @param arc Any Arc within the Scene.
     */
    private void animateTile(Arc arc) {
        disableAllArcActions(true);
        SequentialTransition animation = new SequentialTransition();
        animation.setCycleCount(1);
        animation.setAutoReverse(true);
        FillTransition transition = createFillTransition(arc, 250);
        animation.getChildren().add(transition);
        animation.setOnFinished(event -> disableAllArcActions(false));
        // Once the animation finishes, a check is performed to see if the new pattern should be played.
        // This solves an animation timing issue.
        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disableAllArcActions(false);
                if (simonSays.getCurrentIndex() == 0) {
                    startGameTimer();
                }
            }
        });
        animation.play();
    }

    /**
     * Generates and plays a Sequential Transition according to the current pattern.
     * The Sequential Transition animates each Arc by making it "light" up.
     */
    private void playPattern() {
        disableAllArcActions(true);
        SequentialTransition animation = new SequentialTransition();
        animation.setCycleCount(1);
        animation.setAutoReverse(true);
        for (String color : simonSays.getPattern()) {
            if (color.equals("Green")) {
                FillTransition greenFill = createFillTransition(greenArc, 500);
                animation.getChildren().add(greenFill);
            } else if (color.equals("Red")) {
                FillTransition redFill = createFillTransition(redArc, 500);
                animation.getChildren().add(redFill);
            } else if (color.equals("Yellow")) {
                FillTransition yellowFill = createFillTransition(yellowArc, 500);
                animation.getChildren().add(yellowFill);
            } else if (color.equals("Blue")) {
                FillTransition blueFill = createFillTransition(blueArc, 500);
                animation.getChildren().add(blueFill);
            }
        }
        animation.setOnFinished(event -> disableAllArcActions(false));
        animation.play();
    }

    /**
     * Generate a FillTransition depending on an Arc and Duration.
     * @param arc Any Arc within the Scene.
     * @param milli Duration of the transition in Milliseconds.
     * @return FillTransition that replicates an Arc "lighting" up.
     */
    private FillTransition createFillTransition(Arc arc, int milli) {
        FillTransition transition = new FillTransition(Duration.millis(milli), arc);
        transition.setAutoReverse(true);
        transition.setFromValue((Color)arc.getFill());
        if (arc == greenArc) {
            transition.setToValue(Color.rgb(0,255,0));
        } else if (arc == redArc) {
            transition.setToValue(Color.rgb(255,0,0));
        } else if (arc == yellowArc) {
            transition.setToValue(Color.rgb(255,255,0));
        } else if (arc == blueArc) {
            transition.setToValue(Color.rgb(0,0,255));
        }
        transition.setCycleCount(2);
        return transition;
    }

    /**
     * Disables and Enables all Arc Actions.
     * @param toggle True or False.
     */
    private void disableAllArcActions(boolean toggle) {
        greenArc.setDisable(toggle);
        redArc.setDisable(toggle);
        yellowArc.setDisable(toggle);
        blueArc.setDisable(toggle);
    }

    /**
     * Ends the current game and shows an Alert.
     * Switches the Scene to the Leaderboard upon Alert closure.
     */
    private void endGame() {
        disableAllArcActions(true);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Game Alert");
        alert.setHeaderText("Game Over");
        alert.setContentText("Oops that wasn't the right choice, the game is now over.");
        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                try {
                    switchScene();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
        alert.show();
    }

    /**
     * Switches the current Scene with the final Scene.
     * @throws IOException
     */
    private void switchScene() throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/SimonSays/GUI/ScoreUI.fxml"));
        Stage currentStage = (Stage) anchorPane.getScene().getWindow();
        currentStage.setScene(new Scene(newRoot, 500, 500));
    }
}
