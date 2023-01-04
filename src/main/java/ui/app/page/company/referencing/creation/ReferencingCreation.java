package ui.app.page.company.referencing.creation;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.referencing.Referencing;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.page.company.form.Form;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ReferencingCreation implements Initializable, Observer {

    @FXML
    private AnchorPane referencingCreation;

    @FXML
    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Date de début", true));
        formController.addField(new LabelField("Date de fin", true));
        formController.addField(new LabelField("Niveau d'importance /5", true));
        //formController.addField(new Select("Entreprise", true, State.getInstance().getCurrentUser().);

        formController.initialize(null, null);
    }

    //TODO: company ?? Besoin d'updateStatus ?? modif $ en € dans advertising

    public void createPriceAlert(Referencing ref, Date startDate, Date endDate, int importanceLevel){
        String price = String.valueOf(Facade.getInstance().calculatePriceReferencing(startDate, endDate, importanceLevel));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Prix total");
        alert.setHeaderText("Le prix calculé de votre référencement est : ");
        alert.setContentText(price + " €");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            ObjectId id = Facade.getInstance().insertOneReferencing(ref);
            Facade.getInstance().updateStatus(id, ref);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String startDateString= labelFieldMap.get("Date de début").toString();
        String endDateString= labelFieldMap.get("Date de fin").toString();
        try {
            SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = pattern.parse(startDateString);
            Date endDate = pattern.parse(endDateString);
            Date now = new Date();

            if (!startDate.before(endDate) || endDate.before(now)) {
                // Invalid startDate and endDate.
                formController.showErrorLabel("Dates invalides");
                return;
            }

            if(startDate.before(now)){
                startDate = now;
            }

            int importanceLevel = Integer.parseInt(labelFieldMap.get("Niveau d'importance /5").toString());
            if(importanceLevel>5 || importanceLevel<=0) {
                formController.showErrorLabel("Niveau d'importance invalide");
                return;
            }

        ObjectId company = new ObjectId("63a81022d84f20569350aecd");

        Referencing ref = new Referencing(now, startDate, endDate, importanceLevel, company);

        // The form is valid. Try to create the referencing.
        createPriceAlert(ref, startDate, endDate, importanceLevel);
        } catch (Exception e) {
            formController.showErrorLabel("Format du formulaire invalide. ");
        }
    }
}
