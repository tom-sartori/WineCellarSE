package ui.app.page.user.register;

import exception.InvalidUsernameException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.labelfield.labelfieldmasked.LabelFieldMasked;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Register implements Initializable, Observer {

    @FXML
    private AnchorPane register;

    @FXML
    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Nom d'utilisateur", true));
        formController.addField(new LabelFieldMasked("Mot de passe", true));
        formController.addField(new LabelFieldMasked("Confirmer le mot de passe", true));

        formController.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        if (! labelFieldMap.get("Mot de passe").equals(labelFieldMap.get("Confirmer le mot de passe"))) {
            // Passwords do not match.
            formController.showErrorLabel("Les mots de passe ne correspondent pas. ");
            return;
        }

        try {
            // The form is valid. Try to register and log in.
            Facade.getInstance()
                    .register(new User(labelFieldMap.get("Nom d'utilisateur").toString(), labelFieldMap.get("Mot de passe").toString()));

            State.getInstance()
                    .setCurrentUser(Facade.getInstance()
                            .login(labelFieldMap.get("Nom d'utilisateur").toString(), labelFieldMap.get("Mot de passe").toString())
                    );
        }
        catch (InvalidUsernameException e) {
            formController.showErrorLabel("Nom d'utilisateur invalide. ");
        }
    }

    public void setVisible(boolean visible) {
        register.setVisible(visible);
    }
}
