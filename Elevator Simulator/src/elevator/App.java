package elevator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Laster FXML fra /resources/fxml/Main.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(loader.load(), 420, 220);

        // Laster CSS fra /resources/css/style.css
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("JavaFX Test (FXML + CSS + Controller)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}