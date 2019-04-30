package GamblingFX.Controllers;

import GamblingFX.Dependencies.Systems.UserManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;

/**
 * Controller for the Store Scene.
 * @Author Afaq Anwar
 * @Version 02/11/2019
 */
public class StoreController {
    @FXML private JFXButton purchase100;
    @FXML private JFXButton purchase500;
    @FXML private JFXButton purchase1000;
    @FXML private Label balanceLabel;
    @FXML private StackPane stackPane;
    @FXML private ImageView coinView100;
    @FXML private ImageView coinView500;
    @FXML private ImageView coinView1000;
    private UserManager userManager;

    /**
     * Runs on Scene load.
     * @throws Exception
     */
    public void initialize() throws Exception {
        balanceLabel.setText(Integer.toString(LoginController.currentUser.getBalance()));
        loadCoinImages();
        userManager = new UserManager();
    }

    /**
     * Loads all the proper Images for the Coin selection.
     * @throws Exception
     */
    private void loadCoinImages() throws Exception {
        Image coins100 = new Image(new FileInputStream("src/GamblingFX/Resources/Images/100Coins.png"));
        Image coins500 = new Image(new FileInputStream("src/GamblingFX/Resources/Images/500Coins.png"));
        Image coins1000 = new Image(new FileInputStream("src/GamblingFX/Resources/Images/1000Coins.png"));
        coinView100.setImage(coins100);
        coinView500.setImage(coins500);
        coinView1000.setImage(coins1000);
    }

    /**
     * Validates the purchase by making sure the User has less than 100,000 in their balance.
     * @return true or false depending on if they passed the check.
     */
    private boolean validatePurchase() { return LoginController.currentUser.getBalance() < 100000; }

    /**
     * Purchase Button Handler.
     * Updates the Balance and shows the respective Dialog.
     * @param event Any JFXButton ActionEvent.
     */
    @FXML
    private void handlePurchases(ActionEvent event) {
        JFXDialog dialog = null;
        disableAllButtons(true);
        if (validatePurchase()) {
            if (event.getSource() == purchase100) {
                userManager.updatePlayerBalance(LoginController.currentUser, LoginController.currentUser.getBalance() + 100);
                balanceLabel.setText(Integer.toString(LoginController.currentUser.getBalance()));
                dialog = new JFXDialog();
                dialog.setContent(new Label("100 Christ Coins have been added!"));
                dialog.show(stackPane);
            } else if (event.getSource() == purchase500) {
                userManager.updatePlayerBalance(LoginController.currentUser, LoginController.currentUser.getBalance() + 500);
                balanceLabel.setText(Integer.toString(LoginController.currentUser.getBalance()));
                dialog = new JFXDialog();
                dialog.setContent(new Label("500 Christ Coins have been added!"));
                dialog.show(stackPane);
            } else if (event.getSource() == purchase1000) {
                userManager.updatePlayerBalance(LoginController.currentUser, LoginController.currentUser.getBalance() + 1000);
                balanceLabel.setText(Integer.toString(LoginController.currentUser.getBalance()));
                dialog = new JFXDialog();
                dialog.setContent(new Label("1000 Christ Coins have been added!"));
                dialog.show(stackPane);
            }
        } else {
            dialog = new JFXDialog();
            dialog.setContent(new Label("100,000 COIN MAXIMUM!"));
            dialog.show(stackPane);
        }
        if (dialog != null) {
            final JFXDialog finalDialog = dialog;
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(eventPause -> {
                finalDialog.close();
            });
            pause.play();
        }
        PauseTransition spamFilter = new PauseTransition(Duration.seconds(1));
        spamFilter.setOnFinished(eventFilter -> {
            disableAllButtons(false);
        });
        spamFilter.play();
    }

    /**
     * Disables all JFXButtons within the current Scene.
     * @param bool true or false.
     */
    private void disableAllButtons(Boolean bool) {
        purchase100.setDisable(bool);
        purchase500.setDisable(bool);
        purchase1000.setDisable(bool);
    }
}
