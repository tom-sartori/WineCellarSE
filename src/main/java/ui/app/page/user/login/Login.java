package ui.app.page.user.login;

import exception.BadCredentialException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ui.app.State;
import ui.app.component.errorLabel.ErrorLabel;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private TextField usernameField, passwordField;

    @FXML
    private ErrorLabel errorLabelController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabelController.hide();
    }

    public void submitButtonClicked() {
        if (isFormValid()) {
            hideErrorLabel();

            try {
                // Try to log in.
                State.getInstance().setCurrentUser(
                        Facade.getInstance().login(usernameField.getText(), passwordField.getText())
                );
            }
            catch (BadCredentialException e) {
                // Can not log in due to bad credentials.
                showErrorLabel("Identifiant ou mot de passe incorrect. ");
            }
        }
        else {
            // The form isn't valid.
            showErrorLabel("Veuillez remplir tous les champs. ");
        }
    }

    @FXML
    private void showErrorLabel(String message) {
        errorLabelController.show(message);
    }

    @FXML
    private void hideErrorLabel() {
        errorLabelController.hide();
    }

    private boolean isFormValid() {
        return !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty();
    }
}
