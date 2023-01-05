package ui.app.page.company.event.list;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import persistence.entity.event.Event;
import ui.app.page.company.event.EventCard;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventList implements Initializable {

    @FXML
    private GridPane cardHolder;
    private ObservableList<EventCard> cardList = FXCollections.observableArrayList();

    private final int nbColumn = 2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Event> eventList = Facade.getInstance().getEventList();
        cardList.clear();

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
