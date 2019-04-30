package SimonSays.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller linked to StartUI.fxml
 * @Author Afaq Anwar
 * @Version 01/05/2019
 */
public class StartController {
    private @FXML TextField usernameField;
    private @FXML Button startButton;
    private @FXML Button helpButton;
    private static String username;

    public static String getUsername() { return username; }

    /**
     * Handles the operations of each Button within the current Scene.
     * Clicking the startButton will switch the Scene to the main Game.
     * Clicking the helpButton will show an Alert with information about Simon Says.
     * @param event Current ActionEvent.
     * @throws IOException
     */
    @FXML
    private void handleButtons(ActionEvent event) throws IOException {
        if (event.getSource() == startButton) {
            // A username cannot be only numbers due to the way the CSV is parsed and overwritten.
            if (usernameField.getText().length() == 0 || usernameField.getText().matches("[0-9]+")) {
                Alert alert =  new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Game Alert");
                alert.setHeaderText("USERNAME REQUIRED");
                alert.setContentText("Please provide a valid username...");
                alert.show();
            } else {
                username = usernameField.getText();
                Parent newRoot = FXMLLoader.load(getClass().getResource("/SimonSays/GUI/GameUI.fxml"));
                Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(newRoot, 500, 500));
            }
        } else if (event.getSource() == helpButton) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("How to play Simon Says");
            alert.setContentText("Click the start button in order to start the game." +
                    " Certain pieces will light up, you must click in the sequence that they lit up." +
                    " This will continue until a mistake is made. All high scores can be viewed afterwards.");
            alert.show();
        }
    }
}
