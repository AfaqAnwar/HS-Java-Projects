package GamblingFX.Controllers;

import GamblingFX.Dependencies.Games.Jackpot;
import GamblingFX.Dependencies.Games.jackpotTable;
import com.jfoenix.controls.JFXDialog;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class JackpotController implements Initializable
{
    @FXML private Label yourName;
    @FXML private Label balanceNum;
    @FXML private Label currentBet;
    @FXML private Label timeNum;
    @FXML private Label winPercent;
    @FXML private Button submitButton;
    @FXML private TextField field;
    @FXML private Label prompt;
    @FXML private Label totalBet;
    @FXML private StackPane stackPane;
    @FXML private TableView<jackpotTable> tableView;
    @FXML private TableColumn<jackpotTable, String> nameColumn;
    @FXML private TableColumn<jackpotTable, String> betColumn;
    @FXML private TableColumn<jackpotTable, String> percentColumn;

    private long time2;
    private String winner;
    private double winPercentage;
    private boolean gameState = false;
    private Jackpot jp;

    private int sum;

    private ObservableList<jackpotTable> data = FXCollections.observableArrayList();

    public void start()
    {
        jp.fillPlayerNames();
        jp.fillPlayerPool();
        if (!gameState)
        {
            gameState = true;

            long step = System.nanoTime() + 15000000000L;
            new AnimationTimer()
            {
                public void handle(long now)
                {
                    if (now > step)
                    {
                        timeNum.setText("0");
                    }
                    if (now < step)
                    {
                        time2 = now - step;
                        timeNum.setText(Long.toString(-time2/1000000000));
                        if(Long.toString(-time2/1000000000).equals("0"))
                        {
                            winner = jp.pickWinner();
                            winPercentage = Double.parseDouble(jp.returnWinnerPercent());
                            updateWinnerBalance();
                            balanceNum.setText(Integer.toString(LoginController.currentUser.getBalance()));
                            JFXDialog dialog = new JFXDialog();
                            dialog.setContent(new Label(winner + " won with " + roundDown(winPercentage) + "% chance."));
                            dialog.show(stackPane);
                            PauseTransition pause = new PauseTransition(Duration.seconds(4));
                            pause.setOnFinished(event -> {
                                dialog.close();
                            });
                            pause.play();
                            this.stop();
                        }
                    }
                }
            }.start();
        }
    }

    private void updateWinnerBalance() {
        if (winner.equals(LoginController.currentUser.getFirstName() + " " + LoginController.currentUser.getLastName())) {
            jp.userManager.updatePlayerBalance(LoginController.currentUser, LoginController.currentUser.getBalance() + sum + Integer.parseInt(jp.returnBets(5)));
        }
    }

    private boolean isNumber()
    {
        return (field.getText().matches("^[0-9]*$") || field.getText().length() > 10 || field.getText().length() == 0);
    }

    private double roundDown(double double1)
    {
        return (double)Math.round((double1 * 100))/100;
    }

    public void handler(javafx.event.ActionEvent e)
    {
        if (gameState)
        {
            if(isNumber())
            {
                prompt.setText("Bet Received.");
                currentBet.setText(field.getText());
                sum = 0;
                totalBet.setText(currentBet.getText());
                winPercent.setText(Integer.toString((Integer.valueOf(currentBet.getText()) / Integer.valueOf(totalBet.getText())) * 100) + "%");
                submitButton.setVisible(false);
                field.setEditable(false);
                nameColumn.setCellValueFactory(new PropertyValueFactory<jackpotTable, String>("Name"));
                betColumn.setCellValueFactory(new PropertyValueFactory<jackpotTable, String>("Bet"));
                percentColumn.setCellValueFactory(new PropertyValueFactory<jackpotTable, String>("Percent"));
                runGame();
                jp.userManager.updatePlayerBalance(LoginController.currentUser, LoginController.currentUser.getBalance() - Integer.parseInt(jp.returnBets(5)));
                balanceNum.setText(Integer.toString(LoginController.currentUser.getBalance()));
                for(int i = 0; i < 6; i++)
                {
                sum+=Integer.parseInt(jp.returnBets(i));
                }
                totalBet.setText(Integer.toString(sum));
                winPercent.setText(Double.toString(roundDown(Double.parseDouble(jp.returnPercent(5)))));
                tableView.setItems(getData());
            }
            else
            {
                prompt.setText("Integers Only, 0-100K");
            }
        }
    }

    public ObservableList<jackpotTable> getData()
    {
        for(int i = 0; i < 5; i++) {
            data.add(new jackpotTable(jp.returnNames(i), jp.returnBets(i), Double.toString(roundDown(Double.parseDouble(jp.returnPercent(i))))));
        }
        data.add(new jackpotTable(LoginController.currentUser.getFirstName() + " " + LoginController.currentUser.getLastName(),
                currentBet.getText(),
                Double.toString(roundDown((Double.parseDouble(currentBet.getText()) / Integer.parseInt(totalBet.getText())) * 100))));
        return data;
    }

    public void runGame()
    {
        if (gameState) {
            jp.addPlayerName(5, LoginController.currentUser.getFirstName() + " " + LoginController.currentUser.getLastName());
            jp.addPlayerBet(5, currentBet.getText());
            jp.addPlayerPercent(5);
            jp.addToBettingArr(5);
            jp.returnWinnerPercent();
            gameState = false;
        }
        //Our main problem is that jack actually ran all of this at once which is why the table isn't updating.
        //If you want to fix this problem, I was thinking of actually
        //Filling the table and name first with the bots
        //Then on the handle(button click), it will use the addPlayerName etc to add the player info ONTO the table
        //Then finally when the time counts down to 0 it will run jp.pickWinner instead of runGame all at once
        //I hope this comment helps just contact me on discord for more info about our project.
        //I'm really sorry for the shitty coding cuz this pseudo hardcoding was the easiest way for me to do it.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sum = 0;
        jp = new Jackpot();
        jp.addCurrentPlayer(LoginController.currentUser);
        balanceNum.setText(Integer.toString(LoginController.currentUser.getBalance()));
        yourName.setText(LoginController.currentUser.getFirstName() + " " + LoginController.currentUser.getLastName());
    }
}
