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

import java.awt.*;
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(State.getInstance().getCurrentUser() != null){
            List<Company> companies = Facade.getInstance().findAllCompaniesByUserId(State.getInstance().getCurrentUser().getId());
            if(select.getItems().size() == 0){
                for (Company c : companies){
                    select.getItems().add(c.getName());
                }
            }

            select.setOnAction((event) -> {
                String selectedItem = select.getValue();
                for(Company c : companies){
                    if(c.getName().equals(selectedItem)){
                        list(c.getId());
                    }
                }
            });
        } else {
            select.hide();
            list(null);
        }
    }

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
