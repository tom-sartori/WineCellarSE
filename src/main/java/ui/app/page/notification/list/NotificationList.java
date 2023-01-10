package ui.app.page.notification.list;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.bson.types.ObjectId;
import persistence.entity.notification.Notification;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationList implements Initializable {

    @FXML
    private GridPane cardHolder;

    private ObservableList<NotificationCard> cardList = FXCollections.observableArrayList();
    private final int nbColumn = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObjectId idUser = Facade.getInstance().getLoggedUser().getId();
            List<Notification> notifList = Facade.getInstance().getNotificationListFromUser(idUser);
            Collections.sort(notifList);
            cardList.clear();
            notifList.forEach(notif -> cardList.add(new NotificationCard(notif, this)));
            cardHolder.setAlignment(Pos.CENTER);
            cardHolder.setVgap(30.00);
            cardHolder.setHgap(30.00);
            cardHolder.setStyle("-fx-padding:50px;-fx-alignment: center;");
            onSearch();
        } catch (Exception ignore) { }
    }

    public ObservableList<NotificationCard> getCardList() {
        return cardList;
    }

    public void setCardList(ObservableList<NotificationCard> cardList) {
        this.cardList = cardList;
    }

    @FXML
    public void onSearch() {
        int count = 0;

        cardHolder.getChildren().clear();
        for (NotificationCard card : cardList) {
            cardHolder.add(card, 1, count / nbColumn);
            count++;
        }
    }
}

