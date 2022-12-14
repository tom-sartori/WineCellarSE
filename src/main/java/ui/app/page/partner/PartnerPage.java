package ui.app.page.partner;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.partner.list.PartnerList;

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

    }

    public void onAction() {
        System.out.println("PartnerPage.onAction()");
        partnerListController.initialize(null, null);
    }
}
