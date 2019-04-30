package GamblingFX.Controllers;

import com.jfoenix.controls.JFXSpinner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;

/**
 * Controller for the Loading Screen.
 * @Author Afaq Anwar
 * @Version 02/07/2019
 */
public class LoadingScreenController {
    @FXML private Pane pane;
    @FXML private ImageView logoView;
    @FXML private JFXSpinner loadingSpinner;

    /**
     * Runs on Scene load.
     * @throws Exception
     */
    public void initialize() throws Exception {
        this.loadLogo();
        this.animateSpinner();
    }

    /**
     * Loads the custom made Jesus Casino Text Logo, made by Afaq Anwar. :P
     * @throws Exception
     */
    private void loadLogo() throws Exception {
        Image logo = new Image(new FileInputStream("src/GamblingFX/Resources/Logo/JesusCasino.png"));
        logoView.setImage(logo);
    }

    /**
     * Animates the JFoenix Spinner.
     */
    private void animateSpinner() {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(loadingSpinner.progressProperty(), 0)
                ), new KeyFrame(
                        Duration.seconds(4),
                        new KeyValue(loadingSpinner.progressProperty(), 1)
                )
        );
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    switchToLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Switches the scene to the Login Screen.
     * @throws Exception
     */
    private void switchToLogin() throws Exception {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/Login.fxml"));
        Stage currentStage = (Stage) pane.getScene().getWindow();
        currentStage.setScene(new Scene(newRoot, 600, 400));
        currentStage.getScene().setFill(Color.TRANSPARENT);
    }
}
