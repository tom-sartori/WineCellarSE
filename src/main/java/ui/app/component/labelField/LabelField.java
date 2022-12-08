package ui.app.component.labelField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LabelField implements Initializable {

    @FXML
    private Label label, errorLabel;

    @FXML
    private TextField textField;

    private boolean isRequired;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isRequired = true;
        hideError();
    }

    public void set(String label, boolean isRequired) {
        setLabel(label);
        setRequired(isRequired);
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public String getValue() {
        return textField.getText();
    }

    public boolean isValid() {
        if (isRequired && textField.getText().isEmpty()) {
            // The field is required and empty.
            showError();
            return false;
        }
        else {
            // The field is valid.
            hideError();
            return true;
        }
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public void setErrorMessage(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    private void showError() {
        errorLabel.setVisible(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
    }
}
