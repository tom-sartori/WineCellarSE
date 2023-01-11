package ui.app.page.company.event.creation;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.bson.types.ObjectId;
import persistence.entity.company.Company;
import persistence.entity.event.Event;
import persistence.entity.notification.Notification;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;

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
     * Initializes the controller class and create the fields for the form.
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
                Date startDate = pattern.parse(startDateString);
                Date endDate = pattern.parse(endDateString);
                Date now = new Date();

                /**
                 * The event should not finish before it starts.
                 */
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

                //Send the notifications to subscribers of the company.
                List<ObjectId> followers = companySelected.getFollowerList();
                String message = "L'évènement "+event.getName()+" a été créé ! Allez le consulter !";
                Notification notification = new Notification(null, message);
                Facade.getInstance().insertOneNotificationListId(notification, followers);

                // The form is valid. Try to create the referencing.
                Facade.getInstance().insertOneEvent(event);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nouvel évènement");
                alert.setHeaderText(null);
                alert.setContentText("L'évènement " + event.getName() + " a bien été créé !");

                alert.showAndWait();

                new CustomSceneHelper().bringNodeToFront("eventList");
            } catch (Exception e) {
                formController.showErrorLabel("Format du formulaire invalide. ");
            }
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
