package ui.app.page.guides;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.guides.guideCreation.GuideCreation;
import ui.app.page.guides.list.GuideList;

import java.net.URL;
import java.util.ResourceBundle;

public class Guides implements Initializable {

    @FXML
    private AnchorPane guideController;

    @FXML
    private GuideList guideListController;

    @FXML
    private Button addGuideButtonController;




    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void onAction() {
        System.out.println("Guides.onAction()");
        guideListController.initialize(null, null);
    }





}
