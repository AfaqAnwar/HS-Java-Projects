package GamblingFX.Controllers;

import GamblingFX.Dependencies.Systems.CSVReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controller for the Main Dashboard.
 * @Author Afaq Anwar
 * @Version 02/11/2019
 */
public class DashboardController {
    @FXML private JFXTabPane tabPane;
    @FXML private JFXButton quitButton;

    /**
     * Runs on Scene load.
     */
    public void initialize() throws IOException {
        this.buildTabs();
    }

    /**
     * Builds all required tabs, and adds them to the main Tab Pane.
     */
    private void buildTabs() throws IOException {
        Tab crashTab = new Tab("Crash");
        crashTab.setContent(FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/CrashGame.fxml")));
        tabPane.getTabs().add(crashTab);
        Tab jackpotTab = new Tab("Jackpot");
        tabPane.getTabs().add(jackpotTab);
        jackpotTab.setContent(FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/JackpotGame.fxml")));
        Tab rouletteTab = new Tab("Roulette");
        tabPane.getTabs().add(rouletteTab);
        rouletteTab.setContent(FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/RouletteGame.fxml")));
        Tab coinflipTab = new Tab("Coin Flip");
        tabPane.getTabs().add(coinflipTab);
        coinflipTab.setContent(FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/CoinflipGame.fxml")));
        Tab storeTab = new Tab("Store");
        tabPane.getTabs().add(storeTab);
        storeTab.setContent(FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/Store.fxml")));
    }

    /**
     * Updates the User's current Balance within the CSV File upon exiting the application.
     * @throws Exception
     */
    private void writeToFile() throws Exception {
        CSVReader reader = new CSVReader(new File("src/GamblingFX/Dependencies/Systems/Data/LoginData.csv"));
        List<String> existingData = reader.getAllData();
        String[] splitData = existingData.get(LoginController.columnStored).split(",");
        splitData[4] = Integer.toString(LoginController.currentUser.getBalance());
        String finalString = "";
        for (int i = 0; i < splitData.length; i++) {
            if (i == splitData.length - 1) {
                finalString += splitData[i];
            } else {
                finalString += splitData[i] + ",";
            }
        }
        existingData.set(LoginController.columnStored, finalString);
        PrintWriter writer = new PrintWriter(new File("src/GamblingFX/Dependencies/Systems/Data/LoginData.csv"));
        StringBuilder builder = new StringBuilder();
        builder.append("firstname,lastname,username,password,balance");
        builder.append("\n");
        for (String string : existingData) {
            String[] splitStr = string.split(",");
            for (int i = 0; i < splitStr.length; i++) {
                if (i == splitStr.length - 1) {
                    builder.append(splitStr[i]);
                } else {
                    builder.append(splitStr[i]);
                    builder.append(",");
                }
            }
            builder.append("\n");
        }
        writer.write(builder.toString());
        writer.close();

    }

    /**
     * Handler for the quitButton within the Dashboard Controller.
     * @param event Any ActionEvent
     * @throws Exception
     */
    @FXML
    private void handleQuitButton(ActionEvent event) throws Exception {
        if (event.getSource() == quitButton) {
            this.writeToFile();
            Platform.exit();
        }
    }
}
