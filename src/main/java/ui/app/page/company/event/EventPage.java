package ui.app.page.company.event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.company.event.list.EventList;

import java.net.URL;
import java.util.ResourceBundle;

public class EventPage implements Initializable {

    @FXML
    private AnchorPane eventPageController;

    @FXML
    private EventList eventListController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void onAction() {
        eventListController.initialize(null, null);
    }
}
