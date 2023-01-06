package ui.app.page.user.login;

import exception.BadCredentialException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.labelfield.labelfieldmasked.LabelFieldMasked;
import ui.app.component.form.Form;
import ui.app.helpers.services.CustomSceneHelper;

import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Login implements Initializable, Observer {

    @FXML
    private AnchorPane login;

    @FXML
    private Form formController;

    private static final String labelUsername = "Nom d'utilisateur";
    private static final String labelPassword = "Mot de passe";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField(labelUsername, true));
        formController.addField(new LabelFieldMasked(labelPassword, true));

        formController.setSubmitButtonText("Se connecter");

        formController.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        try {
            // Try to log in.
            User user = Facade.getInstance()
                    .login(labelFieldMap.get(labelUsername).toString(), labelFieldMap.get(labelPassword).toString());
            State.getInstance().setCurrentUser(user);   /// FIXME : should be removed.

            CustomSceneHelper customSceneHelper = new CustomSceneHelper();
            customSceneHelper.refreshMenu();
            customSceneHelper.bringNodeToFront("profile");
        }
        catch (BadCredentialException e) {
            // Can not log in due to bad credentials.
            formController.showErrorLabel("Identifiant ou mot de passe incorrect. ");
        }
    }

    public void setVisible(boolean visible) {
        login.setVisible(visible);
    }
}
