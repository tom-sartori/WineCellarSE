package ui.app.page.company.referencing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.company.referencing.list.ReferencingList;

import java.net.URL;
import java.util.ResourceBundle;

public class ReferencingPage implements Initializable {

    @FXML
    private AnchorPane referencingPageController;

    @FXML
    private ReferencingList referencingListController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void onAction() {
        System.out.println("AdvertisingPage.onAction()");
        referencingListController.initialize(null, null);
    }
}
