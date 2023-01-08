package ui.app.page.company.form.create;

import constant.NodeCreations;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.cellar.Cellar;
import persistence.entity.company.Company;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.list.CompanyList;

import java.net.URL;
import java.util.*;

public class CompanyCreate implements Initializable, Observer {

    @FXML
    private AnchorPane titlePaneCreateBottle;

    @FXML
    private AnchorPane mainPaneCreateBottle;

    @FXML
    private Form formController;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneCreateBottle.getChildren().add(new Label("Création d'une entreprise"));

        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Nom de la société",true));
        formController.addField(new LabelField("Type entreprise",true));
        formController.addField(new LabelField("Description", false));
        formController.addField(new LabelField("Adresse",true));
        formController.addField(new LabelField("Téléphone", false));
        formController.addField(new LabelField("Site web",false));

        formController.setSubmitButtonText("Demander la création");

        formController.initialize(null, null);

        //TODO later : add a logo field
        //        formController.addField(new LabelField("Logo", true));
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO verif les champs

        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String name = labelFieldMap.get("Nom de la société").toString();
        String type = labelFieldMap.get("Type entreprise").toString();
        String description = labelFieldMap.get("Description").toString();
        String address = labelFieldMap.get("Adresse").toString();
        String phoneNumber = labelFieldMap.get("Téléphone").toString();
        String websiteLink = labelFieldMap.get("Site web").toString();

        try {
            ObjectId userId = Facade.getInstance().getLoggedUser().getId();

            Company company = new Company(name,type,address, false, userId,new ArrayList<>(), new Cellar(), description, phoneNumber, websiteLink, null);

            Facade.getInstance().insertOneCompany(company);

            Alert alert = NodeCreations.createAlert("Confirmation de votre demande", "Demande de création envoyée", "Votre demande de création d'entreprise a bien été envoyée. Elle sera traitée dans les plus brefs délais.", Alert.AlertType.INFORMATION);
            alert.show();
            sceneHelper.bringNodeToFront(CompanyList.class.getSimpleName());
        } catch (NoLoggedUser e) {
            Alert erreur = NodeCreations.createAlert("Erreur", "Vous devez être connecté pour créer une entreprise", e.getMessage(), Alert.AlertType.ERROR);
            erreur.show();
        }


    }
}
