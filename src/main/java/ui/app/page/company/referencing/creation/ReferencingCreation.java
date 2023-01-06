package ui.app.page.company.referencing.creation;

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
import ui.app.page.company.form.Form;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReferencingCreation implements Initializable, Observer {

    @FXML
    private AnchorPane referencingCreation;

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

            formController.addField(new LabelField("Date de début", true));
            formController.addField(new LabelField("Date de fin", true));
            formController.addField(new LabelField("Niveau d'importance /5", true));
            formController.addField(new Select("Entreprise", true, companyList));
            //formController.addField(new Select("Entreprise", true, State.getInstance().getCurrentUser().);

            formController.initialize(null, null);
        }
    }

    /**
     * Create the price alert for the advertising.
     * @param ref the referencing.
     * @param startDate the startDate of the advertising.
     * @param endDate the endDate of the advertising.
     * @param importanceLevel the importanceLevel of the advertising.
     */
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
                Date startDate = pattern.parse(startDateString);
                Date endDate = pattern.parse(endDateString);
                Date now = new Date();

                /**
                 * The referencing should not have a startDate that is before the endDate and should not be already finished.
                 */
                if (!startDate.before(endDate) || endDate.before(now)) {
                    // Invalid startDate and endDate.
                    formController.showErrorLabel("Dates invalides");
                    return;
                }

                /**
                 * The referencing should not start before now.
                 */
                if(startDate.before(now)){
                    startDate = now;
                }

                for(Company c : companies) {
                    if(c.getName().equals(labelFieldMap.get("Entreprise").toString())){
                        companySelected = c;
                    }
                }

                /**
                 * The importanceLevel shoul be between 1 and 5.
                 */
                int importanceLevel = Integer.parseInt(labelFieldMap.get("Niveau d'importance /5").toString());
                if(importanceLevel>5 || importanceLevel<=0) {
                    formController.showErrorLabel("Niveau d'importance invalide");
                    return;
                }

                Referencing ref = new Referencing(now, startDate, endDate, importanceLevel, companySelected.getId());

                // The form is valid. Try to create the referencing.
                createPriceAlert(ref, startDate, endDate, importanceLevel);
            } catch (Exception e) {
                formController.showErrorLabel("Format du formulaire invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
