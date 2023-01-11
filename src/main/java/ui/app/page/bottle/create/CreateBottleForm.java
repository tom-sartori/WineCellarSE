package ui.app.page.bottle.create;

import constant.NodeCreations;
import exception.BadArgumentsException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import persistence.entity.bottle.Bottle;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.cellar.details.CellarDetails;

import java.net.URL;
import java.util.*;

public class CreateBottleForm implements Initializable, Observer {

    @FXML
    private AnchorPane titlePaneCreateBottle;

    @FXML
    private AnchorPane mainPaneCreateBottle;

    @FXML
    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneCreateBottle.getChildren().add(new Label("Créer une bouteille"));

        formController.addObserver(this);

        formController.clearFieldList();

        formController.addField(new LabelField("Nom de la bouteille", false));
        formController.addField(new LabelField("Millésime", false));
        formController.addField(new LabelField("Appellation", true));
        formController.addField(new LabelField("Catégorie", true));
        formController.addField(new LabelField("Producteur", true));
        formController.addField(new LabelField("Pourcentage d'alcool", true));
        formController.addField(new LabelField("Taille de la bouteille", true));
        formController.addField(new LabelField("Unité de la taille de la bouteille", true));
        formController.addField(new LabelField("Image de la bouteille", false));
        formController.addField(new LabelField("Prix", false));
        formController.addField(new LabelField("Raisins", true));

        formController.initialize(null, null);

    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String name = labelFieldMap.get("Nom de la bouteille").toString();
        int vintage = Integer.parseInt(labelFieldMap.get("Millésime").toString());
        String appellation = labelFieldMap.get("Appellation").toString();
        String bottleImage = labelFieldMap.get("Image de la bouteille").toString();
        double price = Double.parseDouble(labelFieldMap.get("Prix").toString());
        String producer = labelFieldMap.get("Producteur").toString();
        double alcoholPercentage = Double.parseDouble(labelFieldMap.get("Pourcentage d'alcool").toString());
        double bottleSize = Double.parseDouble(labelFieldMap.get("Taille de la bouteille").toString());
        String bottleSizeUnit = labelFieldMap.get("Unité de la taille de la bouteille").toString();
        String category = labelFieldMap.get("Catégorie").toString();
        String grapes = labelFieldMap.get("Raisins").toString();

        ArrayList<String> grapeList = new ArrayList<>(Arrays.asList(grapes.split(",")));

        Bottle bottle = new Bottle(name,vintage,appellation,bottleImage, price, producer, alcoholPercentage, bottleSize, bottleSizeUnit, category, grapeList);

        try {
            if (Facade.getInstance().isManagerOfCellar(State.getInstance().getSelectedCellar().getId()) && State.getInstance().getSelectedWall() != null && State.getInstance().getSelectedEmplacementBottle() != null && State.getInstance().getSelectedCellar() != null){
                try {
                    Facade.getInstance().insertBottle(State.getInstance().getSelectedWall(), State.getInstance().getSelectedCellar(), bottle, State.getInstance().getSelectedEmplacementBottle());
                    CustomSceneHelper sceneHelper = new CustomSceneHelper();
                    sceneHelper.bringNodeToFront(CellarDetails.class.getSimpleName());
                } catch (BadArgumentsException e) {
                    Alert alert = NodeCreations.createAlert("Mauvais arguments", "Entrez des arguments valides", e.getMessage(), Alert.AlertType.ERROR);
                    alert.show();
                }
            }
        } catch (Exception e) {
            Alert alert = NodeCreations.createAlert("Aucun utilisateur connecté", "Veuillez vous connectez", e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }
}
