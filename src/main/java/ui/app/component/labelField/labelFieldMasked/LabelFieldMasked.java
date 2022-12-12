package ui.app.component.labelField.labelFieldMasked;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.app.component.labelField.LabelField;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LabelFieldMasked extends LabelField implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    public LabelFieldMasked() {
        // Should not be called. Use the constructor with parameters instead.
        super();
    }

    public LabelFieldMasked(String labelText, boolean isRequired) {
        // The component has been imported from the constructor ok a .java class.
        super();
        try {
            labelField = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LabelFieldMasked.fxml")));
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
}
