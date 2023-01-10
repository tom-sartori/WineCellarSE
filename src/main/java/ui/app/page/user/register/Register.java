package ui.app.page.user.register;

import exception.InvalidUsernameException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.notification.Notification;
import persistence.entity.user.User;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.labelfield.labelfieldmasked.LabelFieldMasked;
import ui.app.component.form.Form;
import ui.app.helpers.services.CustomSceneHelper;

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

    private static final String labelUsername = "Nom d'utilisateur";
    private static final String labelPassword = "Mot de passe";
    private static final String labelConfirmPassword = "Confirmer le mot de passe";
    private static final String labelFirstname = "Prénom";
    private static final String labelLastname = "Nom";
    private static final String labelEmail = "Email";


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField(labelUsername, true));
        formController.addField(new LabelField(labelEmail, true));
        formController.addField(new LabelField(labelFirstname, false));
        formController.addField(new LabelField(labelLastname, false));
        formController.addField(new LabelFieldMasked(labelPassword, true));
        formController.addField(new LabelFieldMasked(labelConfirmPassword, true));

        formController.setSubmitButtonText("S'inscrire");

        formController.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        if (! labelFieldMap.get(labelPassword).equals(labelFieldMap.get(labelConfirmPassword))) {
            // Passwords do not match.
            formController.showErrorLabel("Les mots de passe ne correspondent pas. ");
            return;
        }

        try {
            // The form is valid. Try to register and log in.
            ObjectId userId = Facade.getInstance()
                    .register(
                            new User(
                                    labelFieldMap.get(labelUsername).toString(),
                                    labelFieldMap.get(labelPassword).toString(),
                                    labelFieldMap.get(labelFirstname).toString(),
                                    labelFieldMap.get(labelLastname).toString(),
                                    labelFieldMap.get(labelEmail).toString()
                            )
                    );

            Facade.getInstance().insertOneNotification(
                    new Notification(
                            userId,
                            "Votre compte a été créé avec succès. Bienvenue !"
                    ));

            Facade.getInstance()
                    .login(labelFieldMap.get(labelUsername).toString(), labelFieldMap.get(labelPassword).toString());

            CustomSceneHelper customSceneHelper = new CustomSceneHelper();
            customSceneHelper.refreshMenu();
            customSceneHelper.bringNodeToFront("profile");
        }
        catch (InvalidUsernameException e) {
            formController.showErrorLabel("Nom d'utilisateur invalide. ");
        }
    }

    public void setVisible(boolean visible) {
        register.setVisible(visible);
    }
}
