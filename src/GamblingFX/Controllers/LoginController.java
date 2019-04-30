package GamblingFX.Controllers;

import GamblingFX.Dependencies.Systems.CSVReader;
import GamblingFX.Dependencies.Systems.User;
import com.jfoenix.controls.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controller for the Login Scene.
 * @Author Afaq Anwar
 * @Version 02/11/2019
 */
public class LoginController {
    @FXML private Pane pane;
    @FXML private ImageView logoView;
    @FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private StackPane stackPane;
    private CSVReader csvReader;
    protected static User currentUser;
    // Stores the column the current User's data is stored in.
    protected static Integer columnStored;
    private boolean dialogOpen;

    /**
     * Runs on Scene load.
     * @throws Exception
     */
    public void initialize() throws Exception {
        this.loadLogo();
        File protectedData = new File("src/GamblingFX/Dependencies/Systems/Data/LoginData.csv");
        csvReader = new CSVReader(protectedData);
        dialogOpen = false;
    }

    /**
     * Loads the custom made Jesus Casino Logo, made by Afaq Anwar. :P
     * @throws Exception
     */
    private void loadLogo() throws Exception {
        Image logo = new Image(new FileInputStream("src/GamblingFX/Resources/Logo/JesusCasinoText.png"));
        logoView.setImage(logo);
    }

    /**
     * Method called on the Login Button click.
     * Checks if the current combination of the username and password exist and are correct.
     * Switches the scene to the Dashboard and initializes the current User if True, otherwise it displays an error.
     * @throws IOException
     */
    @FXML
    private void checkLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (csvReader.getDataString(3).contains(username)) {
            String correctUserPassword = csvReader.getDataString(4).get(csvReader.getDataString(3).indexOf(username));
            if (password.equals(correctUserPassword)) {
                columnStored = csvReader.getDataString(3).indexOf(username);
                currentUser = this.buildUser();
                this.allowEntry();
            } else {
                if (!dialogOpen) {
                    JFXDialog dialog = new JFXDialog();
                    dialog.setContent(new Label("Error: Incorrect Password!"));
                    dialog.show(stackPane);
                    dialogOpen = true;
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> {
                        dialog.close();
                        dialogOpen = false;
                    });
                    pause.play();
                }
            }
        } else {
            if (!dialogOpen) {
                JFXDialog dialog = new JFXDialog();
                dialog.setContent(new Label("Error: User Does Not Exist!"));
                dialog.show(stackPane);
                dialogOpen = true;
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    dialog.close();
                    dialogOpen = false;
                });
                pause.play();
            }
        }
    }

    /**
     * Allows entry into the Main Application Dashboard.
     * @throws IOException
     */
    private void allowEntry() throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/Dashboard.fxml"));
        Stage currentStage = (Stage) pane.getScene().getWindow();
        currentStage.setScene(new Scene(newRoot, 800, 600));
        currentStage.getScene().setFill(Color.TRANSPARENT);
    }

    /**
     * Method called on Create Account Button click.
     * Switches to the Account Creation Scene.
     * @throws IOException
     */
    @FXML
    private void allowAccountCreation() throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/AccountCreation.fxml"));
        Stage currentStage = (Stage) pane.getScene().getWindow();
        currentStage.setScene(new Scene(newRoot, 600, 400));
        currentStage.getScene().setFill(Color.TRANSPARENT);
    }

    /**
     * Initializes the current User.
     * @return User that represents that current User.
     */
    private User buildUser() {
        for (String currStr : csvReader.getAllData()) {
            if (currStr.contains(usernameField.getText())) {
                String[] splitStr = currStr.split(",");
                String firstName = splitStr[0];
                String lastName = splitStr[1];
                int balance = Integer.parseInt(splitStr[splitStr.length - 1]);
                return new User(firstName, lastName, balance);
            }
        }
        return null;
    }

    /**
     * Method that is called with the quitButton is clicked.
     */
    @FXML
    private void quit() {
        Platform.exit();
    }
}