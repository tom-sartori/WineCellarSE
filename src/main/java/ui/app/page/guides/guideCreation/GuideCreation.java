package ui.app.page.guides.guideCreation;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistence.entity.guide.Guide;
import persistence.entity.guide.GuideCategory;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.guides.list.GuideList;

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


    private GuideCategory selectedCategory;
    private static int nbClique = 0;

    CustomSceneHelper sceneHelper = new CustomSceneHelper();

    Label label = new Label();

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


            // The form is valid.
            Facade.getInstance()
                    .insertOneGuide(new Guide(labelFieldMap.get("Titre du guide").toString(), labelFieldMap.get("description").toString(), sectionMap, selectedCategory, new Date()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Nouveau guide ajouté !");
            Optional<ButtonType> option = alert.showAndWait();

        }
        catch (Exception e) {
            formController.showErrorLabel("Erreur");
        }
    }

    public void setVisible(boolean visible) {
        guideCreation.setVisible(visible);
    }

    public void ajoutParagraphe(){
        label.setText("Bonjour");
    }

    public void onAction() {
        this.initialize(null, null);
    }

    public void goBack(){
        sceneHelper.bringNodeToFront(GuideList.class.getSimpleName());
    }
}
