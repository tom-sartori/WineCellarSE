package ui.app.component.errorlabel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorLabel implements Initializable {

    @FXML
    private Label label;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void show(String message) {
        label.setText(message);
        label.setVisible(true);
    }

    public void hide() {
        label.setVisible(false);
    }
}
