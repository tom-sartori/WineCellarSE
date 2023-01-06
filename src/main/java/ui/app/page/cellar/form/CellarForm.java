package ui.app.page.cellar.form;

import exception.BadCredentialException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import persistence.entity.cellar.Cellar;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.labelfield.labelfieldmasked.LabelFieldMasked;
import ui.app.component.form.Form;
import ui.app.helpers.services.CustomSceneHelper;

import java.net.URL;
import java.util.*;

public class CellarForm implements Initializable, Observer {

    @FXML
    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Nom de la cave", true));

        formController.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        // TODO handle possible errors
        Cellar cellar = new Cellar(labelFieldMap.get("Nom de la cave").toString(),false, new ArrayList<>(), new ArrayList<>(),State.getInstance().getCurrentUser().getId(),new ArrayList<>());
        try {
            Facade.getInstance().insertOneCellar(cellar);
            State.getInstance().setSelectedCellar(cellar);
            CustomSceneHelper sceneHelper = new CustomSceneHelper();
            sceneHelper.bringNodeToFront("cellarDetails");
        } catch (BadCredentialException e) {
            formController.showErrorLabel("Vous n'êtes pas connecté");
        }
    }
}
