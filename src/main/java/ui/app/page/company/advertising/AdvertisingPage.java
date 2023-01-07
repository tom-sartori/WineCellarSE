package ui.app.page.company.advertising;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.company.advertising.details.AdvertisingDetails;
import ui.app.page.company.advertising.list.AdvertisingList;

import java.net.URL;
import java.util.ResourceBundle;

public class AdvertisingPage implements Initializable {

    @FXML
    private AnchorPane advertisingPageController;

    @FXML
    private AdvertisingList advertisingListController;

    @FXML
    private AdvertisingDetails advertisingDetailsController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void onAction() {
        System.out.println("AdvertisingPage.onAction()");
        advertisingListController.initialize(null, null);
        advertisingDetailsController.initialize(null, null);
    }
}
