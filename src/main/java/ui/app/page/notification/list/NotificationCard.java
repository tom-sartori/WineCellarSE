package ui.app.page.notification.list;

import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import persistence.entity.notification.Notification;

import java.util.Optional;

public class NotificationCard extends Pane {

    private Notification notif;
    private NotificationList notifList;
    private Label date;
    private TextArea message;
    private Button boutonSuppression;

    public NotificationCard(Notification notif, NotificationList notifList){
        this.notif = notif;
        this.notifList = notifList;

        date = new Label();
        message = new TextArea();

        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center; -fx-border-width: 1px");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);

        date.setText(notif.getDate().toString());
        message.setWrapText(true);

        message.setEditable(false);
        message.setText(notif.getMessage());
        message.setLayoutX(69.0);
        message.setLayoutY(35.0);
        message.setPrefHeight(70.0);
        message.setPrefWidth(249.0);

        getChildren().addAll(date, message);

        boutonSuppression = new Button();
        boutonSuppression.setText("x");
        boutonSuppression.setLayoutX(100.0);

        getChildren().add(boutonSuppression);

        boutonSuppression.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimerNotification();
            }

        });


    }

    public void supprimerNotification(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cette notification ?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() != ButtonType.CANCEL){
            Facade.getInstance().deleteOneNotification(notif.getId());
        }
        notifList.initialize(null, null);
    }
}
