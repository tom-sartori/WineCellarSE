package ui.app.page.company.event.list;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.bson.types.ObjectId;
import persistence.entity.company.Company;
import persistence.entity.event.Event;
import ui.app.State;
import ui.app.page.company.event.EventCard;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventList implements Initializable {

    @FXML
    private GridPane cardHolder;

    @FXML
    private ChoiceBox<String> select;
    private ObservableList<EventCard> cardList = FXCollections.observableArrayList();
    private final int nbColumn = 2;

    /**
     * Initializes the controller class and the select field.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Facade.getInstance().isUserLogged()){
            select.setVisible(true);

            /**
             * The companies where the user is manager or all the companies existing if the user is admin.
             */
            List<Company> companies;
            if(Facade.getInstance().isAdminLogged()){
                companies = Facade.getInstance().getCompanyList();
            } else {
                companies = Facade.getInstance().findAllCompaniesByUserId(State.getInstance().getCurrentUser().getId());
            }

            if(select.getItems().size() == 0){
                select.getItems().add("Toutes");
                for (Company c : companies){
                    select.getItems().add(c.getName());
                }
            }
            //pre-select the "Toutes" choice
            select.getSelectionModel().selectFirst();

            /**
             * If "Toutes" is selected, create a list with all the referencings else
             * retrieve the company selected and create a list of referencing.
             */
            select.setOnAction((event) -> {
                String selectedItem = select.getValue();
                if(selectedItem.equals("Toutes")){
                    list(null);
                } else {
                    for(Company c : companies){
                        if(c.getName().equals(selectedItem)){
                            list(c.getId());
                        }
                    }
                }
            });
        } else {
            select.setVisible(false);
            list(null);
        }
    }

    /**
     * Get the events thanks to the company selected and put them in the list.
     * @param company the company selected.
     */
    public void list(ObjectId company){
        cardList.clear();
        List<Event> eventList;

        if(company != null){
            eventList = Facade.getInstance().getEventsByCompany(company);
        } else {
            eventList = Facade.getInstance().getEventList();
        }

        eventList.forEach(event -> cardList.add(new EventCard(event)));

        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(30.00);
        cardHolder.setHgap(30.00);
        cardHolder.setStyle("-fx-padding:80px;-fx-alignment: center;");

        onSearch();
    }

    @FXML
    public void onSearch() {
        int count = 0;

        cardHolder.getChildren().clear();
        for (EventCard card : cardList) {
            cardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }
}
