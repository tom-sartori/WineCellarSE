package ui.app.page.company.referencing.update;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.company.Company;
import persistence.entity.referencing.Referencing;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.company.referencing.list.ReferencingList;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReferencingUpdate implements Initializable, Observer {

    @FXML
    private AnchorPane referencingCreation;

    @FXML
    private Form formController;

    private String[] companyList;

    private List<Company> companies;

    private Company companySelected;

    private Referencing referencing;

    private CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class and create the fields for the form.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null) {
            referencing = State.getInstance().getCurrentReferencing();

            String startDate=new SimpleDateFormat("dd/MM/yyyy").format(referencing.getStartDate());
            String endDate=new SimpleDateFormat("dd/MM/yyyy").format(referencing.getExpirationDate());

            companies = Facade.getInstance().getCompanyList();
            companyList = new String[companies.size()];
            int i = 0;
            for (Company c : companies) {
                companyList[i] = c.getName();
                i++;
            }

            Company company = Facade.getInstance().getOneCompany(referencing.getCompany());
            Select select = new Select("Entreprise", true, companyList);
            select.getChoiceBox().getSelectionModel().select(company.getName());

            formController.addObserver(this);

            formController.clearFieldList();

            formController.addField(new LabelField("Date de début", startDate, true));
            formController.addField(new LabelField("Date de fin", endDate, true));
            formController.addField(new LabelField("Niveau d'importance /5", String.valueOf(referencing.getImportanceLevel()), true));
            formController.addField(select);
            //formController.addField(new Select("Entreprise", true, State.getInstance().getCurrentUser().);
            formController.setSubmitButtonText("Payer");
            formController.initialize(null, null);
        }
    }

    /**
     * Create the price alert
     * @param ref the updated referencing
     * @param id the id of the referencing
     * @param price the price to pay
     */
    public void createPriceAlert(Referencing ref, ObjectId id, String price){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Prix total");
        alert.setHeaderText("Le prix calculé de ce référencement est : ");
        alert.setContentText(price + " €");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            Facade.getInstance().updateOneReferencing(id, ref);
            Facade.getInstance().updateStatus(id, ref);
        }
    }

    /**
     * Check the dates, importanceLevel and company selected fields and create the referencing.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(State.getInstance().getCurrentUser() != null){
            Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

            String startDateString= labelFieldMap.get("Date de début").toString();
            String endDateString= labelFieldMap.get("Date de fin").toString();
            try {
                SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
                Date newStartDate = pattern.parse(startDateString);
                Date newEndDate = pattern.parse(endDateString);
                Date now = new Date();

                /**
                 * The referencing should not have a startDate that is before the endDate and should not be already finished.
                 */
                if (!newStartDate.before(newEndDate) || newEndDate.before(now)) {
                    // Invalid startDate and endDate.
                    formController.showErrorLabel("Dates invalides");
                    return;
                }

                /**
                 * The referencing should not start before now.
                 */
                if(newStartDate.before(now)){
                    newStartDate = now;
                }

                for(Company c : companies) {
                    if(c.getName().equals(labelFieldMap.get("Entreprise").toString())){
                        companySelected = c;
                    }
                }

                /**
                 * The importanceLevel should be between 1 and 5.
                 */
                int importanceLevel = Integer.parseInt(labelFieldMap.get("Niveau d'importance /5").toString());
                if(importanceLevel>5 || importanceLevel<=0) {
                    formController.showErrorLabel("Niveau d'importance invalide");
                    return;
                }

                Referencing ref = new Referencing(now, newStartDate, newEndDate, importanceLevel, companySelected.getId());
                double price = Facade.getInstance().calculatePriceReferencing(newStartDate, newEndDate, importanceLevel);
                if(price > referencing.getPrice()){
                    createPriceAlert(ref,referencing.getId(), String.valueOf(price));
                }
                sceneHelper.bringNodeToFront(ReferencingList.class.getSimpleName());
            } catch (Exception e) {
                formController.showErrorLabel("Format du formulaire invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
