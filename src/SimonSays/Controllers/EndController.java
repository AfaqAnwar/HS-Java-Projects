package SimonSays.Controllers;

import SimonSays.Dependencies.CSVReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller linked to ScoreUI.fxml
 * @Author Afaq Anwar
 * @Version 01/05/2019
 */
public class EndController {
    @FXML private Label firstPlace;
    @FXML private Label secondPlace;
    @FXML private Label thirdPlace;
    @FXML private Label fourthPlace;
    @FXML private Label fifthPlace;
    @FXML private Label userScore;
    @FXML Button restartButton;
    @FXML Button exitButton;

    String currentUser = StartController.getUsername();
    int currentScore = GameController.getScore();

    private CSVReader reader;
    private PrintWriter writer;
    private StringBuilder stringBuilder;
    private File file;

    private List<String> data;
    private ArrayList<String> topFive;

    /**
     * Reads the current scores from scores.csv and writes the current score to the csv.
     * All Labels are also updated.
     * @throws FileNotFoundException
     */
    public void initialize() throws FileNotFoundException {
        file = new File("src/SimonSays/Dependencies/scores.csv");
        reader = new CSVReader(file);
        writer = new PrintWriter(file);
        stringBuilder = new StringBuilder();
        data = reader.getAllData();
        addCurrentData();
        Collections.sort(data);
        Collections.reverse(data);
        writeToFile();
        topFive = generateTopFive(data);
        updateLabels();
    }

    /**
     * Appends the current game results to the List of Data.
     *      In case the user already exists and the current score is the same as last time, the old data is removed
     *      in order to avoid redundant duplicates on the leaderboard.
     */
    private void addCurrentData() {
        if (userExists() != -1 && currentScore == Integer.parseInt(data.get(userExists()).substring(0,1))) {
            data.remove(userExists());
        }
        if (currentScore > 0) {
            data.add(currentScore + "," + currentUser);
        }
    }

    /**
     * Checks if the current username exists within the scores.csv data.
     * @return If true, Integer that represents the index of the data entry.
     *         Else -1
     */
    private int userExists() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).contains(currentUser)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Overwrite the CSV file with new data.
     *      StringBuilder was used in order to optimize for future MS EXCEL Parsing.
     */
    private void writeToFile() {
        stringBuilder.append("Score");
        stringBuilder.append(",");
        stringBuilder.append("Username");
        stringBuilder.append("\n");

        for (String string : data) {
            String[] splitStr = string.split(",");
            for (int i = 0; i < splitStr.length; i++) {
                if (i == splitStr.length - 1) {
                    stringBuilder.append(splitStr[i]);
                } else {
                    stringBuilder.append(splitStr[i]);
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("\n");
        }

        writer.write(stringBuilder.toString());
        writer.close();
    }

    /**
     * Generates the list of the top 5 players.
     * @param dataList List of Strings that represents the sorted data.
     * @return ArrayList of Strings that contains the top 5 players.
     *              Format - "USERNAME - SCORE"
     */
    private ArrayList<String> generateTopFive(List<String> dataList) {
        ArrayList<String> topFiveList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                String[] splitStr = dataList.get(i).split(",");
                topFiveList.add(splitStr[1] + " - " + splitStr[0]);
            } catch (IndexOutOfBoundsException e) {
                topFiveList.add("EMPTY");
            }
        }
        return topFiveList;
    }

    /**
     * Updates all labels within the Scene.
     */
    private void updateLabels() {
        firstPlace.setText(topFive.get(0));
        secondPlace.setText(topFive.get(1));
        thirdPlace.setText(topFive.get(2));
        fourthPlace.setText(topFive.get(3));
        fifthPlace.setText(topFive.get(4));
        userScore.setText(Integer.toString(currentScore));
    }

    /**
     * Handles all Buttons.
     * If the restartButton is clicked the Scene will switch back to the Game.
     * If the exitButton is clicked the application will close.
     * @param event The current ActionEvent.
     * @throws IOException
     */
    @FXML
    private void handleButtons(ActionEvent event) throws IOException {
        if (event.getSource() == restartButton) {
            Parent newRoot = FXMLLoader.load(getClass().getResource("/SimonSays/GUI/GameUI.fxml"));
            Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(newRoot, 500, 500));
        } else if (event.getSource() == exitButton) {
            Platform.exit();
        }
    }
}
