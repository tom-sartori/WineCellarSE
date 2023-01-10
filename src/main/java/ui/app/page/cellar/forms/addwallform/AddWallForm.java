package ui.app.page.cellar.forms.addwallform;

import exception.BadArgumentsException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.Wall;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.details.CellarDetails;

import java.net.URL;
import java.util.*;

public class AddWallForm implements Initializable, Observer {

    @FXML
    private AnchorPane titlePaneCreateWall;

    @FXML
    private AnchorPane mainPaneCreateWall;

    @FXML
    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneCreateWall.getChildren().add(new Label("Créer un mur"));

        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Nom du mur", true));
        formController.addField(new LabelField("image", false));

        formController.initialize(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String name = labelFieldMap.get("Nom du mur").toString();
        String image = labelFieldMap.get("image").toString();

        Wall wall = new Wall(image, new ArrayList<>(), name);

        if (State.getInstance().getSelectedCellar() != null && Facade.getInstance().isManagerOfCellar(State.getInstance().getSelectedCellar().getId())){
            try {
                Facade.getInstance().addWall(wall, State.getInstance().getSelectedCellar().getId());
                CustomSceneHelper sceneHelper = new CustomSceneHelper();
                sceneHelper.bringNodeToFront(CellarDetails.class.getSimpleName());
            } catch (BadArgumentsException e) {
                formController.showErrorLabel("Mauvais arguments");
            }
        }
    }

}
