package ui.app.page.user.profile;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import persistence.entity.user.User;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Profile implements Initializable, Observer {

    @FXML
    private Form formController;

    private static final String labelUsername = "Nom d'utilisateur";
    private static final String labelFirstname = "Prénom";
    private static final String labelLastname = "Nom";
    private static final String labelEmail = "Email";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formController.addObserver(this);

        formController.setSubmitButtonVisibility(false);
        formController.clearFieldList();

        try {
            User loggedUser = Facade.getInstance().getLoggedUser();

            formController.addField(new LabelField(labelUsername, loggedUser.getUsername(), false));

            if (loggedUser.getFirstname() != null) {
                // Optional field. If null, don't display it.
                formController.addField(new LabelField(labelFirstname, loggedUser.getFirstname(), false));
            }

            if (loggedUser.getLastname() != null) {
                // Optional field. If null, don't display it.
                formController.addField(new LabelField(labelLastname, loggedUser.getLastname(), false));
            }

            if (loggedUser.getEmail() != null) {
                // Optional field. If null, don't display it.
                formController.addField(new LabelField(labelEmail, loggedUser.getEmail(), false));
            }

            formController.initialize(null, null);
        }
        catch (NoLoggedUser e) {
            formController.initialize(null, null);
            formController.showErrorLabel("Erreur, aucun utilisateur connecté. ");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // Submit button is not visible, so this method is not called.
    }
}
