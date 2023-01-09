package ui.app.page.notification;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.notification.list.NotificationList;

import java.net.URL;
import java.util.ResourceBundle;


public class NotificationPage implements Initializable {
    @FXML
    private AnchorPane notificationPageController;

    //@FXML
    //private NotificationList notificationListController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAction(){
        this.initialize(null,null);
    }


}
