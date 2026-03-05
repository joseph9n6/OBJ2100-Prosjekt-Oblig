package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Hello extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Label("Hello, JavaFX!"), 320, 180));
        stage.setTitle("HelloFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}