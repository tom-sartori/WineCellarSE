package ui.app.page.rates.list;

import constant.NodeCreations;
import exception.BadCredentialException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.bson.types.ObjectId;
import persistence.entity.cellar.Cellar;
import persistence.entity.rate.Rate;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;

import java.net.URL;
import java.util.*;

public class RateList implements Initializable {

    @FXML
    private GridPane cardHolder;

    @FXML
    private HBox hbox;



    private ObservableList<RateCard> cardList = FXCollections.observableArrayList();
    private final int nbColumn = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                if (Facade.getInstance().isUserLogged()){
                    formulaireCreation();
                }

        List<Rate> rateList = Facade.getInstance().getRateList();
        cardList.clear();

        rateList.forEach(rate -> cardList.add(new RateCard(rate, this)));

        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(30.00);
        cardHolder.setHgap(30.00);
        cardHolder.setStyle("-fx-padding:50px;-fx-alignment: center;");

        onSearch();
    }

    public ObservableList<RateCard> getCardList() {
        return cardList;
    }

    public void setCardList(ObservableList<RateCard> cardList) {
        this.cardList = cardList;
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

    public void formulaireCreation(){
        hbox.setAlignment(Pos.CENTER);
        LabelField labelField = new LabelField("Ajouter un commentaire", true);
        Button createRateButton = new Button("Créer");
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(5);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);

        hbox.getChildren().addAll(slider,labelField, createRateButton);

        createRateButton.setOnAction(event -> {
            int sliderValue = (int) Math.round(slider.getValue());
            // Récupère le texte écrit dans le LabelField
            String text = labelField.getValue();
            ObjectId ownerRef = new ObjectId("63b8cb0d459add6fa390fcc0");
            ObjectId subjectRef = new ObjectId("63a5c45048f0c9194a9295ef");
            Rate rate = new Rate(ownerRef, subjectRef, sliderValue, text, false, new Date());
            try {
                Facade.getInstance().insertOneRate(rate);
                slider.setValue(1);

                cardList.add(new RateCard(rate, this));
                onSearch();
            } catch (BadCredentialException e) {
                NodeCreations.createAlert("Erreur", "Erreur lors de la création de l'avis", e.getMessage(), Alert.AlertType.ERROR);
            }

        });
    }

}
