package ui.app.component.field.select;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.app.component.field.Field;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class Select extends Field implements Initializable {

    @FXML
    private AnchorPane select;

    @FXML
    protected Label label, errorLabel;

    @FXML
    private ChoiceBox<String> choiceBox;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isRequired = true;
        hideError();
    }

    public Select() {
        // Should not be called. Use the constructor with parameters instead.
        super();
    }

    public Select(String labelText, boolean isRequired, String[] options) {
        // The component has been imported from the constructor ok a .java class.
        super();
        try {
            select = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Select.fxml")));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        getChildren().add(select);
        label = (Label) select.lookup("#label");
        errorLabel = (Label) select.lookup("#errorLabel");
        choiceBox = (ChoiceBox<String>) select.lookup("#choiceBox");

        Arrays.stream(options).forEach(choiceBox.getItems()::add);

        this.label.setText(labelText);
        this.isRequired = isRequired;
    }

    @Override
    public String getLabel() {
        return label.getText();
    }

    @Override
    public String getValue() {
        return choiceBox.getValue();
    }

    @Override
    public void showError() {
        errorLabel.setVisible(true);
    }

    @Override
    public void hideError() {
        errorLabel.setVisible(false);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        choiceBox.setDisable(readOnly);
    }
}
