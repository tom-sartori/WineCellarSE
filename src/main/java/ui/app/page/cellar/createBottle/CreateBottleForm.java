package ui.app.page.cellar.createBottle;

import com.mongodb.lang.Nullable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistence.entity.bottle.Bottle;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.*;

public class CreateBottleForm implements Initializable, Observer {

    @FXML
    private AnchorPane titlePaneCreateBottle;

    @FXML
    private AnchorPane mainPaneCreateBottle;

//    @FXML
//    private Form formController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        titlePaneCreateBottle.getChildren().add(new Label("Créer une bouteille"));
//
//        formController.addObserver(this);
//
//        formController.clearFieldList();
//
//        formController.addField(new LabelField("Nom de la bouteille", false));
//        formController.addField(new LabelField("Millésime", false));
//        formController.addField(new LabelField("Appellation", true));
//        formController.addField(new LabelField("Catégorie", true));
//        formController.addField(new LabelField("Producteur", true));
//        formController.addField(new LabelField("Pourcentage d'alcool", true));
//        formController.addField(new LabelField("Taille de la bouteille", true));
//        formController.addField(new LabelField("Unité de la taille de la bouteille", true));
//        formController.addField(new LabelField("Image de la bouteille", false));
//        formController.addField(new LabelField("Prix", false));
//        formController.addField(new LabelField("Raisins", true));
//
//        formController.initialize(null, null);

    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        String name = labelFieldMap.get("Nom de la bouteille").toString();
        int vintage = Integer.parseInt(labelFieldMap.get("Millésime").toString());
        String appellation = labelFieldMap.get("Appellation").toString();
        String category = labelFieldMap.get("Catégorie").toString();
        String producer = labelFieldMap.get("Producteur").toString();
        double alcoholPercentage = Double.parseDouble(labelFieldMap.get("Pourcentage d'alcool").toString());

        //TODO faire
//        Bottle bottle = new Bottle(name, vintage, appellation, category, producer, alcoholPercentage, labelFieldMap.get("Taille de la bouteille").toString(), labelFieldMap.get("Unité de la taille de la bouteille").toString(), labelFieldMap.get("Image de la bouteille").toString(), labelFieldMap.get("Prix").toString(), labelFieldMap.get("Raisins").toString());
        // TODO
    }
}
