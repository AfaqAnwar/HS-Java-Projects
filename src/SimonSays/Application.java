package SimonSays;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Simon Says in Java!
 * @Author Afaq Anwar
 * @Version 01/05/2019
 */
public class Application extends javafx.application.Application {

    /**
     * Sets up the first Scene of the Application.
     * @param primaryStage The main Stage.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/SimonSays/GUI/StartUI.fxml"));
        primaryStage.setTitle("Simon Says");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
