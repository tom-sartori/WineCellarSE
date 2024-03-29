package ui.app.page.guides.guideCreation;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistence.entity.guide.Guide;
import persistence.entity.guide.GuideCategory;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.helpers.CustomSceneHelper;

import java.net.URL;
import java.util.*;

public class GuideCreation implements Initializable, Observer {
    //public AnchorPane guideCreation;
    @FXML
    private AnchorPane guideCreation;

    @FXML
    private Form formController;

    @FXML
    private Pane paneForm;

    @FXML
    private ComboBox<GuideCategory> categoryComboBox;

    @FXML
    private Button boutonAjoutParagraphe;

    @FXML
    private AnchorPane anchorForm;

    private GuideCategory selectedCategory;
    private static int nbClique = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            formController.addObserver(this);

            formController.clearFieldList();

            formController.addField(new LabelField("Titre du guide", true));
            formController.addField(new LabelField("description", true));
            formController.initialize(null, null);

            boutonAjoutParagraphe.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    nbClique+=1;
                    formController.addField(new LabelField("titre paragraphe "+nbClique, true));
                    formController.addField(new LabelField("contenu "+nbClique, true));
                    //formController.getVBox().getChildren().add(new Button("supprimer"));
                    formController.initialize(null, null);
                    ///TODO changer boutton submit

                }
            });
            //guideCreation.getChildren().add(boutonAjoutParagraphe);

            // Créer une liste observable des valeurs de l'énumération GuideCategory
            ObservableList<GuideCategory> categories = FXCollections.observableArrayList(GuideCategory.values());

            // Définir la liste observable comme source de données pour le ComboBox
            categoryComboBox.setItems(categories);

            // Définir un gestionnaire d'évènements pour la sélection d'un élément dans le ComboBox
            categoryComboBox.setOnAction(event -> {
                // Récupérer la catégorie sélectionnée
                selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            });
        }




    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;
        try {
            LinkedHashMap<String, String> sectionMap = new LinkedHashMap<>();
            for (int i = 1; i <= nbClique; i++) {
                if (labelFieldMap.get("titre paragraphe "+i) != null && labelFieldMap.get("contenu "+i) != null) {
                    sectionMap.put(labelFieldMap.get("titre paragraphe "+i).toString(),labelFieldMap.get("contenu "+i).toString());
                }
            }

            if(selectedCategory != null){
                // The form is valid.
                Facade.getInstance()
                        .insertOneGuide(new Guide(labelFieldMap.get("Titre du guide").toString(), labelFieldMap.get("description").toString(), sectionMap, selectedCategory, new Date()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Nouveau guide ajouté !");
                Optional<ButtonType> option = alert.showAndWait();

                new CustomSceneHelper().bringNodeToFront("guideList");
            }else {
                // Afficher l'alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez sélectionner une catégorie pour votre guide !");
                alert.showAndWait();
            }

        }
        catch (Exception e) {
            formController.showErrorLabel("Erreur");
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }
}
