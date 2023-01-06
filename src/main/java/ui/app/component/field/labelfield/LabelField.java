package ui.app.component.field.labelfield;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ui.app.component.field.Field;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LabelField extends Field implements Initializable {

    @FXML
    protected AnchorPane labelField;

    @FXML
    protected Label label, errorLabel;

    @FXML
    protected TextField textField;


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

        this.label.setText(labelText);
        this.isRequired = isRequired;
    }

    public LabelField(String labelText, String textFieldValue, boolean isRequired) {
        // The component has been imported from the constructor ok a .java class.
        this(labelText, isRequired);
        this.textField.setText(textFieldValue);
    }


    @Override
    public String getLabel() {
        return label.getText();
    }

    @Override
    public String getValue() {
        return textField.getText();
    }

    @Override
    public void showError() {
        errorLabel.setVisible(true);
    }

    @Override
    public void hideError() {
        errorLabel.setVisible(false);
    }
}
