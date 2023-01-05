package ui.app.page.company.event.creation;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import persistence.entity.company.Company;
import persistence.entity.event.Event;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.page.company.form.Form;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventCreation implements Initializable, Observer {

    @FXML
    private AnchorPane eventCreation;

    @FXML
    private Form formController;

    private String[] companyList;

    private List<Company> companies;

    private Company companySelected;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null){
            companies = Facade.getInstance().findAllCompaniesByUserId(State.getInstance().getCurrentUser().getId());
            companyList = new String[companies.size()];
            int i = 0;
            for(Company c : companies){
                companyList[i] = c.getName();
                i++;
            }

            formController.addObserver(this);

            formController.clearFieldList();

            formController.addField(new LabelField("Nom", true));
            formController.addField(new LabelField("Adresse", true));
            formController.addField(new LabelField("Description", true));
            formController.addField(new LabelField("Date de début", true));
            formController.addField(new LabelField("Date de fin", true));
            formController.addField(new Select("Entreprise", true, companyList));

            //formController.addField(new Select("Entreprise", true, State.getInstance().getCurrentUser().);

            formController.initialize(null, null);
        }
    }

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

                if (!startDate.before(endDate)) {
                    // Invalid startDate and endDate.
                    formController.showErrorLabel("Dates invalides");
                    return;
                }

                for(Company c : companies) {
                    if(c.getName().equals(labelFieldMap.get("Entreprise").toString())){
                        companySelected = c;
                    }
                }

                Event event = new Event(labelFieldMap.get("Nom").toString(), labelFieldMap.get("Adresse").toString(), labelFieldMap.get("Description").toString(), startDate, endDate, companySelected.getId());

                // The form is valid. Try to create the referencing.
                Facade.getInstance().insertOneEvent(event);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nouvel évènement");
                alert.setHeaderText(null);
                alert.setContentText("L'évènement " + event.getName() + " a bien été créé !");

                alert.showAndWait();
            } catch (Exception e) {
                formController.showErrorLabel("Format du formulaire invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
