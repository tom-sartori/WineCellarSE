package ui.app.page.notification.list;

import facade.Facade;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import persistence.entity.notification.Notification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Objects;
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

        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center; -fx-border-width: 1px; -fx-padding: 15px" );
        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        date.setText(formatter.format(notif.getDate()));
        date.setLayoutY(5.0);
        date.setLayoutX(5.0);
        message.setWrapText(true);
        message.setStyle("-fx-border-color: transparent;");

        message.setEditable(false);
        message.setText(notif.getMessage());
        message.setLayoutX(100.0);
        message.setLayoutY(35.0);
        message.setPrefHeight(55.0);
        message.setPrefWidth(250.0);

        getChildren().addAll(date, message);

        ImageView trash = new ImageView();
        try {
            trash.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/trash.png")).getPath())));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int photoSize = 20;
        trash.setFitHeight(photoSize);
        trash.setFitWidth(photoSize);
        trash.setLayoutX(400.0);
        trash.setLayoutY(5.0);
        getChildren().add(trash);

        trash.setOnMouseClicked(e -> {
            supprimerNotification();
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
