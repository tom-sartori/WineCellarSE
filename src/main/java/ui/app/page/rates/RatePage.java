package ui.app.page.rates;

import facade.Facade;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bson.types.ObjectId;
import persistence.entity.guide.Guide;
import persistence.entity.rate.Rate;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.page.rates.list.RateList;

import java.net.URL;
import java.util.*;

public class RatePage implements Initializable{
    ///TODO faire avec les utilisateurs
    @FXML
    private AnchorPane ratePageController;

    @FXML
    private RateList rateListController;

    @FXML
    private Form formController;

    // Ajoutez une référence à l'élément ListView
    @FXML
    private ListView<Label> commentList;

    @FXML
    private VBox vboxTab;

    @FXML
    private AnchorPane formPane;


    // Ajoutez une référence à l'objet TextField qui représente le champ de texte du formulaire
    @FXML
    private TextField commentField;

    @FXML
    private HBox hboxForm;


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
