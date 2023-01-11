package ui.app.page.company.advertising.creation;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import persistence.entity.company.Company;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdvertisingCreation implements Initializable, Observer {

    @FXML
    private AnchorPane advertisingCreation;

    @FXML
    private Form formController;

    private String[] companyList;

    private List<Company> companies;

    private Company companySelected;

    /**
     * Initializes the controller class and create the fields for the form.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null) {
            companies = Facade.getInstance().findAllCompaniesByUserId(State.getInstance().getCurrentUser().getId());
            companyList = new String[companies.size()];
            int i = 0;
            for (Company c : companies) {
                companyList[i] = c.getName();
                i++;
            }
            formController.addObserver(this);

            formController.clearFieldList();

            formController.addField(new LabelField("Nom de la publicité", true));
            formController.addField(new LabelField("Description", true));
            formController.addField(new LabelField("Lien de l'entreprise", true));
            formController.addField(new LabelField("Lien de l'image de la publicité", true));
            formController.addField(new LabelField("Date de début", true));
            formController.addField(new LabelField("Date de fin", true));
            formController.addField(new Select("Entreprise", true, companyList));
            formController.setSubmitButtonText("Payer");
            formController.initialize(null, null);
        }
    }

    /**
     * Create the price alert.
     */
    public void createPriceAlert(Advertising ad, Date startDate, Date endDate){
        String price = String.valueOf(Facade.getInstance().calculatePriceAdvertising(startDate, endDate));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Prix total");
        alert.setHeaderText("Le prix calculé de cette publicité est : ");
        alert.setContentText(price + " €");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            ObjectId id = Facade.getInstance().insertOneAdvertising(ad);
            Facade.getInstance().payOneAdvertising(id);

            new CustomSceneHelper().bringNodeToFront("advertisingList");
        }
    }

    /**
     * Check the dates fields and the company selected and create the advertising.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(State.getInstance().getCurrentUser() != null){
            Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

            String startDateString= labelFieldMap.get("Date de début").toString();
            String endDateString= labelFieldMap.get("Date de fin").toString();
            try {
                Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
                Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);
                Date now = new Date();

                if (!startDate.before(endDate)) {
                    // Invalid startDate and endDate.
                    formController.showErrorLabel("Dates invalides");
                    return;
                }

                if(startDate.before(now)){
                    startDate = now;
                }

                for(Company c : companies) {
                    if(c.getName().equals(labelFieldMap.get("Entreprise").toString())){
                        companySelected = c;
                    }
                }

                Advertising ad = new Advertising(labelFieldMap.get("Nom de la publicité").toString(), labelFieldMap.get("Description").toString(), labelFieldMap.get("Lien de l'entreprise").toString(), labelFieldMap.get("Lien de l'image de la publicité").toString(), startDate, endDate, companySelected.getId());

                // The form is valid. Try to create the advertising.
                createPriceAlert(ad, startDate,endDate);
            } catch (Exception e) {
                formController.showErrorLabel("Format de dates invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
