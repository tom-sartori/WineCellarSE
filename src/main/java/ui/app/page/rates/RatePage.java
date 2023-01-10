package ui.app.page.rates;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.app.page.rates.list.RateList;

import java.net.URL;
import java.util.ResourceBundle;

public class RatePage implements Initializable{
    ///TODO faire avec les utilisateurs
    @FXML
    private AnchorPane ratePageController;

    @FXML
    private RateList rateListController;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void onAction() {
        rateListController.initialize(null, null);
    }




}
