package ui.app.page.guides.list;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import persistence.entity.guide.Guide;
import persistence.entity.guide.GuideCategory;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.guides.guideCreation.GuideCreation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class GuideList implements Initializable {
    @FXML
    private GridPane cardHolder;

    @FXML
    private ComboBox<GuideCategory> categoryFiltreComboBox;

    private GuideCategory selectedCategoryFiltreLabel;

    private ObservableList<GuideCard> cardList = FXCollections.observableArrayList();

    private final int nbColumn = 1;
    CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Guide> guideList = Facade.getInstance().getGuideList();
        cardList.clear();

        int maxWidth = 1280;
        int gapBetweenCard = 20;
        double preferredHeight = 230.0;
        double preferredWidth = (maxWidth - (nbColumn + 1) * gapBetweenCard) / nbColumn;
        guideList.forEach(guide -> cardList.add(new GuideCard(guide, preferredHeight, preferredWidth)));
        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-padding:10px;");

        onSearch();

        // Créer une liste observable des valeurs de l'énumération GuideCategory
        ObservableList<GuideCategory> categories = FXCollections.observableArrayList(GuideCategory.values());

        // Définir la liste observable comme source de données pour le ComboBox
        categoryFiltreComboBox.setItems(categories);

        // Définir un gestionnaire d'évènements pour la sélection d'un élément dans le ComboBox
        categoryFiltreComboBox.setOnAction(event -> {
            // Récupérer la catégorie sélectionnée
            selectedCategoryFiltreLabel = categoryFiltreComboBox.getSelectionModel().getSelectedItem();
        });
    }

    @FXML
    public void onSearch() {
        int count = 0;

        cardHolder.getChildren().clear();
        for (GuideCard card : cardList) {
            cardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    public void goToCreationPage(){
        sceneHelper.bringNodeToFront(GuideCreation.class.getSimpleName());
    }

    public void onAction() {
        this.initialize(null, null);
    }
}
