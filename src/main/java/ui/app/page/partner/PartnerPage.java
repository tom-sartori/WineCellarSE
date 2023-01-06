package ui.app.page.partner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.partner.list.PartnerList;

import java.net.URL;
import java.util.ResourceBundle;

public class PartnerPage implements Initializable {

    @FXML
    private AnchorPane partnerPageController;

    @FXML
    private PartnerList partnerListController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partnerListController.initialize(null, null);
    }

    public void onAction() {
        partnerListController.initialize(null, null);
    }
}
