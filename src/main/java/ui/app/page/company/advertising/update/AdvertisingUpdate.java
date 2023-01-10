package ui.app.page.company.advertising.update;

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
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.advertising.list.AdvertisingList;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdvertisingUpdate implements Initializable, Observer {

    @FXML
    private AnchorPane advertisingUpdate;

    @FXML
    private Form formController;

    private String[] companyList;

    private List<Company> companies;

    private Company companySelected;

    private Advertising advertising;

    private CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class and create the fields for the form.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null) {
            advertising = State.getInstance().getCurrentAdvertising();

            String startDate=new SimpleDateFormat("dd/MM/yyyy").format(advertising.getStartDate());
            String endDate=new SimpleDateFormat("dd/MM/yyyy").format(advertising.getEndDate());

            companies = Facade.getInstance().getCompanyList();
            companyList = new String[companies.size()];
            int i = 0;
            for (Company c : companies) {
                companyList[i] = c.getName();
                i++;
            }

            Company company = Facade.getInstance().getOneCompany(advertising.getCompany());

            formController.addObserver(this);
            formController.clearFieldList();

            Select select = new Select("Entreprise", true, companyList);
            select.getChoiceBox().getSelectionModel().select(company.getName());

            formController.addField(new LabelField("Nom de la publicité", advertising.getName(), true));
            formController.addField(new LabelField("Description", advertising.getDescription(), true));
            formController.addField(new LabelField("Lien de l'entreprise", advertising.getUrl(), true));
            formController.addField(new LabelField("Lien de l'image de la publicité", advertising.getLink(), true));
            formController.addField(new LabelField("Date de début", startDate, true));
            formController.addField(new LabelField("Date de fin", endDate, true));
            formController.addField(select);
            formController.setSubmitButtonText("Payer");
            formController.initialize(null, null);
        }
    }

    /**
     * Create the price alert
     * @param ad the updated advertising
     * @param id the id of the advertising
     * @param price the price to pay
     */
    public void createPriceAlert(Advertising ad, ObjectId id, String price){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Prix total");
        alert.setHeaderText("Le prix calculé de cette publicité est : ");
        alert.setContentText(price + " €");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            Facade.getInstance().updateOneAdvertising(id, ad);
            Facade.getInstance().payOneAdvertising(id);
        }
    }

    /**
     * Check the dates fields and the company selected and update the advertising.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(State.getInstance().getCurrentUser() != null){
            Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

            String startDateString= labelFieldMap.get("Date de début").toString();
            String endDateString= labelFieldMap.get("Date de fin").toString();
            try {
                Date newStartDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
                Date newEndDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);
                Date now = new Date();

                if (!newStartDate.before(newEndDate)|| newEndDate.before(now)) {
                    // Invalid startDate and endDate.
                    formController.showErrorLabel("Dates invalides");
                    return;
                }

                if(newStartDate.before(now)){
                    newStartDate = now;
                }

                for(Company c : companies) {
                    if(c.getName().equals(labelFieldMap.get("Entreprise").toString())){
                        companySelected = c;
                    }
                }

                Advertising ad = new Advertising(labelFieldMap.get("Nom de la publicité").toString(), labelFieldMap.get("Description").toString(), labelFieldMap.get("Lien de l'entreprise").toString(), labelFieldMap.get("Lien de l'image de la publicité").toString(), newStartDate, newEndDate, companySelected.getId());

                // The form is valid. Try to update the advertising.
                double price = Facade.getInstance().calculatePriceAdvertising(newStartDate, newEndDate);
                if(price > advertising.getPrice()){
                    createPriceAlert(ad, advertising.getId(), String.valueOf(price-advertising.getPrice()));
                }

                sceneHelper.bringNodeToFront(AdvertisingList.class.getSimpleName());
            } catch (Exception e) {
                formController.showErrorLabel("Format de dates invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
