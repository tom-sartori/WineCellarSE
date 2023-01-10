package ui.app.page.guides.guideModification;

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
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.*;

public class GuideModification implements Initializable, Observer {
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

    private Guide guide;

    private LinkedHashMap<String, String> sectionMap = new LinkedHashMap<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Facade.getInstance().isAdminLogged()){
            guide = State.getInstance().getCurrentGuide();

            formController.addObserver(this);
            formController.clearFieldList();
            formController.addField(new LabelField("Titre du guide", guide.getTitle(), true));
            formController.addField(new LabelField("description", guide.getDescription(), true));

            LinkedHashMap<String, String> section = new LinkedHashMap<>();
            section = guide.getSectionList();
            int nbTour = 0;
            for(Map.Entry<String,String> e : section.entrySet()){
                nbTour+=1;
                formController.addField(new LabelField("titre paragraphe "+nbTour,e.getKey(), true));
                formController.addField(new LabelField("contenu "+nbTour, e.getValue(), true));
                formController.initialize(null, null);
            }
            nbClique = nbTour;
            sectionMap = section;

            formController.initialize(null, null);
            boutonAjoutParagraphe.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    nbClique+=1;
                    formController.addField(new LabelField("titre paragraphe "+nbClique, true));
                    formController.addField(new LabelField("contenu "+nbClique, true));
                    //formController.getVBox().getChildren().add(new Button("supprimer"));
                    formController.initialize(null, null);

                }
            });
            //guideCreation.getChildren().add(boutonAjoutParagraphe);

            // Créer une liste observable des valeurs de l'énumération GuideCategory
            ObservableList<GuideCategory> categories = FXCollections.observableArrayList(GuideCategory.values());

            // Définir la liste observable comme source de données pour le ComboBox
            categoryComboBox.setItems(categories);

            categoryComboBox.getSelectionModel().select(guide.getCategory());
            selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();

            // Définir un gestionnaire d'évènements pour la sélection d'un élément dans le ComboBox
            categoryComboBox.setOnAction(event -> {
                // Récupérer la catégorie sélectionnée
                selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            });
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;
        try {
            for (int i = 1; i <= nbClique; i++) {
                if (labelFieldMap.get("titre paragraphe "+i) != null && labelFieldMap.get("contenu "+i) != null) {
                    sectionMap.put(labelFieldMap.get("titre paragraphe "+i).toString(),labelFieldMap.get("contenu "+i).toString());
                }
            }

            if(selectedCategory != null){
                // The form is valid.
                Facade.getInstance()
                        .updateOneGuide(guide.getId(), new Guide(labelFieldMap.get("Titre du guide").toString(), labelFieldMap.get("description").toString(), sectionMap, selectedCategory, new Date()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Guide modifié !");
                Optional<ButtonType> option = alert.showAndWait();

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
