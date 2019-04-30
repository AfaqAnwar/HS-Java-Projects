module HS.Java.Projects {
    requires org.jsoup;
    requires com.jfoenix;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    exports SimonSays to javafx.graphics;
    opens SimonSays.Controllers to javafx.fxml;
    exports SimonSays.Controllers to javafx.graphics, javafx.fxml;
    exports GamblingFX to javafx.graphics;
    opens GamblingFX.Controllers to javafx.fxml;
    exports GamblingFX.Controllers to javafx.graphics, javafx.fxml;
}