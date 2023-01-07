package ui.app.page.rates.list;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import persistence.entity.rate.Rate;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RateList implements Initializable {

    @FXML
    private GridPane cardHolder;

    private ObservableList<RateCard> cardList = FXCollections.observableArrayList();
    private final int nbColumn = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Rate> rateList = Facade.getInstance().getRateList();
        cardList.clear();

        rateList.forEach(rate -> cardList.add(new RateCard(rate)));

        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(30.00);
        cardHolder.setHgap(30.00);
        cardHolder.setStyle("-fx-padding:50px;-fx-alignment: center;");

        onSearch();
    }

    @FXML
    public void onSearch() {
        int count = 0;

        cardHolder.getChildren().clear();
        for (RateCard card : cardList) {
            cardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }

}
