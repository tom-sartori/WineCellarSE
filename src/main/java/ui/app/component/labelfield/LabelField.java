package ui.app.component.labelfield;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LabelField extends Pane implements Initializable {

    @FXML
    protected AnchorPane labelField;

    @FXML
    protected Label label, errorLabel;

    @FXML
    protected TextField textField;

    private boolean isRequired;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isRequired = true;
        hideError();
    }

    public LabelField() {
        // Should not be called. Use the constructor with parameters instead.
        super();
    }

    public LabelField(String labelText, boolean isRequired) {
        // The component has been imported from the constructor ok a .java class.
        super();
        try {
            labelField = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LabelField.fxml")));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        getChildren().add(labelField);
        label = (Label) labelField.lookup("#label");
        errorLabel = (Label) labelField.lookup("#errorLabel");
        textField = (TextField) labelField.lookup("#textField");

        setLabel(labelText);
        setRequired(isRequired);
    }

    public void set(String label, boolean isRequired) {
        setLabel(label);
        setRequired(isRequired);
    }

    public void setLabel(String labelText) {
        this.label.setText(labelText);
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

    public String getLabel() {
        return label.getText();
    }
}
