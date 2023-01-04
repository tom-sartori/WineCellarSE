package ui.app.page.company.referencing.list;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import persistence.entity.referencing.Referencing;
import ui.app.page.company.referencing.ReferencingCard;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReferencingList implements Initializable {

    @FXML
    private GridPane cardHolder;
    private ObservableList<ReferencingCard> cardList = FXCollections.observableArrayList();

    private final int nbColumn = 4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Referencing> referencingList = Facade.getInstance().getReferencingList();
        cardList.clear();

        int maxWidth = 1280;
        int gapBetweenCard = 20;
        referencingList.forEach(referencing -> cardList.add(new ReferencingCard(referencing)));


        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-padding:10px;");


        onSearch();
    }

    @FXML
    public void onSearch() {
        int count = 0;

        cardHolder.getChildren().clear();
        for (ReferencingCard card : cardList) {
            cardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }
}
