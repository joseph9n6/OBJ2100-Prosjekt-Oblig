package elevator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainCtrl {

    @FXML private Label statusLabel;

    private int count = 0;

    @FXML
    private void onClick() {
        count++;
        statusLabel.setText("Klikk registrert: " + count + " ✅");
    }
}