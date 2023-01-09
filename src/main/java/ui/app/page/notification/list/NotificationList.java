package ui.app.page.notification.list;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import org.bson.types.ObjectId;
import persistence.entity.notification.Notification;
import ui.app.page.rates.list.RateCard;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class NotificationList implements Initializable {

    @FXML
    private GridPane cardHolder;

    private ObservableList<NotificationCard> cardList = FXCollections.observableArrayList();
    private final int nbColumn = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {


            ///TODO faire avec l id de l'utilisateur connecte, ici camarche pas
            //ObjectId idUser = Facade.getInstance().getLoggedUser().getId();
            //System.out.println(idUser);
            List<Notification> notifList = Facade.getInstance().getNotificationListFromUser(new ObjectId("63b8cb0d459add6fa390fcc0"));
            //List<Notification> notifList = Facade.getInstance().getNotificationListFromUser(idUser);
            //System.out.println(notifList.size());
            //List<Notification> notifList = Facade.getInstance().getNotificationList();
            cardList.clear();

            notifList.forEach(notif -> cardList.add(new NotificationCard(notif, this)));

            cardHolder.setAlignment(Pos.CENTER);
            cardHolder.setVgap(30.00);
            cardHolder.setHgap(30.00);
            cardHolder.setStyle("-fx-padding:50px;-fx-alignment: center;");

            onSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            cardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }

    public void test(){
        System.out.println("uhuigyuigyugygyugyyu");
    }
}

