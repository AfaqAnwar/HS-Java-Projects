package GamblingFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Starts the Gambling Game Application.
 * @Author Afaq Anwar
 * @Version 02/08/2019
 */
public class Application extends javafx.application.Application {
    /**
     * Sets up the first Scene of the Application.
     * @param primaryStage The main Stage.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GamblingFX/Resources/FXML/LoadingScreen.fxml"));
        // Font loading, due to JavaFX 2.2 not being able to call fonts directly through the CSS File.
        Font.loadFont(getClass().getResourceAsStream("/GamblingFX/Resources/Fonts/Quicksand-Bold.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/GamblingFX/Resources/Fonts/Quicksand-Light.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/GamblingFX/Resources/Fonts/Quicksand-Medium.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/GamblingFX/Resources/Fonts/Quicksand-Regular.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/GamblingFX/Resources/Fonts/Staatliches-Regular.ttf"), 10);
        primaryStage.setTitle("Loading Screen");
        primaryStage.setScene(new Scene(root, 600, 400));

        // Removes the borders and generic bar above the window.
        primaryStage.getScene().setFill(Color.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
