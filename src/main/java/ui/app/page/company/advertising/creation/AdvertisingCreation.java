package ui.app.page.company.advertising.creation;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import ui.app.component.field.labelfield.LabelField;
import ui.app.page.company.advertising.form.Form;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdvertisingCreation implements Initializable, Observer {

    @FXML
    private AnchorPane advertisingCreation;

    @FXML
    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Nom de la publicité", true));
        formController.addField(new LabelField("Description", true));
        formController.addField(new LabelField("Lien de l'entreprise", true));
        formController.addField(new LabelField("Lien de l'image de la publicité", true));
        formController.addField(new LabelField("Date de début", true));
        formController.addField(new LabelField("Date de fin", true));

        formController.initialize(null, null);
    }

    //TODO: company ??
    //TODO: Bouton nom submit

    public void createPriceAlert(Advertising ad, Date startDate, Date endDate){
        String price = String.valueOf(Facade.getInstance().calculatePrice(startDate, endDate));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Prix total");
        alert.setHeaderText("Le prix calculé de cette publicité est : ");
        alert.setContentText(price + " $");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            ObjectId id = Facade.getInstance().insertOneAdvertising(ad);
            Facade.getInstance().payOneAdvertising(id);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String startDateString= labelFieldMap.get("Date de début").toString();
        String endDateString= labelFieldMap.get("Date de fin").toString();
        try {
            Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
            Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);

            if (!startDate.before(endDate)) {
                // Invalid startDate and endDate.
                formController.showErrorLabel("Dates invalides");
                return;
            }

            ObjectId company = new ObjectId("63a81022d84f20569350aecd");

            Advertising ad = new Advertising(labelFieldMap.get("Nom de la publicité").toString(), labelFieldMap.get("Description").toString(), labelFieldMap.get("Lien de l'entreprise").toString(), labelFieldMap.get("Lien de l'image de la publicité").toString(), startDate, endDate, company);

            // The form is valid. Try to create the advertising.
            createPriceAlert(ad, startDate,endDate);
        } catch (Exception e) {
            formController.showErrorLabel("Format de dates invalide. ");
        }

    }
}
