package ui.app.page.company.event.update;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import persistence.entity.company.Company;
import persistence.entity.event.Event;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.company.list.CompanyList;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventUpdate implements Initializable, Observer {

    @FXML
    private AnchorPane eventCreation;

    @FXML
    private Form formController;

    private String[] companyList;

    private List<Company> companies;

    private Company companySelected;

    private Event event;

    private CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class and create the fields for the form.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null){
            event = State.getInstance().getCurrentEvent();
            String startDate=new SimpleDateFormat("dd/MM/yyyy").format(event.getStartDate());
            String endDate=new SimpleDateFormat("dd/MM/yyyy").format(event.getEndDate());

            companies = Facade.getInstance().getCompanyList();
            companyList = new String[companies.size()];
            int i = 0;
            for(Company c : companies){
                companyList[i] = c.getName();
                i++;
            }

            Company company = Facade.getInstance().getOneCompany(event.getCompany());
            Select select = new Select("Entreprise", true, companyList);
            select.getChoiceBox().getSelectionModel().select(company.getName());

            formController.addObserver(this);

            formController.clearFieldList();

            formController.addField(new LabelField("Nom", event.getName(),true));
            formController.addField(new LabelField("Adresse", event.getAddress(),true));
            formController.addField(new LabelField("Description",event.getDescription(), true));
            formController.addField(new LabelField("Date de début", startDate, true));
            formController.addField(new LabelField("Date de fin", endDate, true));
            formController.addField(select);

            formController.setSubmitButtonText("Ajouter");

            //formController.addField(new Select("Entreprise", true, State.getInstance().getCurrentUser().);

            formController.initialize(null, null);
        }
    }

    /**
     * Check the validity of the dates and the company selected to create the event.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(State.getInstance().getCurrentUser() != null){
            Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

            String startDateString= labelFieldMap.get("Date de début").toString();
            String endDateString= labelFieldMap.get("Date de fin").toString();
            try {
                /**
                 * Transform the date typed by the user into a usable date.
                 */
                SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
                Date newStartDate = pattern.parse(startDateString);
                Date newEndDate = pattern.parse(endDateString);
                Date now = new Date();

                /**
                 * The event should not finish before it starts.
                 */
                if (!newStartDate.before(newEndDate)) {
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

                Event event2 = new Event(labelFieldMap.get("Nom").toString(), labelFieldMap.get("Adresse").toString(), labelFieldMap.get("Description").toString(), newStartDate, newEndDate, companySelected.getId());

                // The form is valid. Try to update the event.
                Facade.getInstance().updateOneEvent(event.getId(), event2);

                sceneHelper.bringNodeToFront(CompanyList.class.getSimpleName());

            } catch (Exception e) {
                formController.showErrorLabel("Format du formulaire invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
