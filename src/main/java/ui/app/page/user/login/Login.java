package ui.app.page.user.login;

import exception.BadCredentialException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import ui.app.State;
import ui.app.component.errorLabel.ErrorLabel;
import ui.app.component.labelField.LabelField;
import ui.app.component.labelField.labelFieldMasked.LabelFieldMasked;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private ErrorLabel errorLabelController;

    @FXML
    private LabelField labelFieldUsernameController;

    @FXML
    private LabelFieldMasked labelFieldPasswordController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelFieldUsernameController.set("Nom d'utilisateur", true);

        labelFieldPasswordController.set("Mot de passe", true);

        errorLabelController.hide();
    }

    public void submitButtonClicked() {
        if (isFormValid()) {
            hideErrorLabel();

            try {
                // Try to log in.
                State.getInstance().setCurrentUser(
                        Facade.getInstance()
                                .login(labelFieldUsernameController.getValue(), labelFieldPasswordController.getValue())
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

    public void onKeyEnterSubmitForm(KeyEvent event) {
        // If the user press enter, we submit the form.
        if (event.getCode().toString().equals("ENTER")) {
            submitButtonClicked();
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
        // For each LabelField, check if it is valid.
        return Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(LabelField.class) || field.getType().equals(LabelFieldMasked.class))
                .map(field -> {
                    try {
                        return (LabelField) field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(labelField -> !labelField.isValid())
                .count() == 0;
    }
}
