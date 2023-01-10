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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.bson.types.ObjectId;
import persistence.entity.rate.Rate;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RateList implements Initializable {

    @FXML
    private GridPane cardHolder;

    @FXML
    private HBox hbox;

    private ObservableList<RateCard> cardList = FXCollections.observableArrayList();
    private final int nbColumn = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbox.getChildren().clear();
        cardHolder.getChildren().clear();
        cardList.clear();
        if (Facade.getInstance().isUserLogged()){
            formulaireCreation();
        }

        if(State.getInstance().getSelectedBottle() != null){
            List<Rate> rateList = Facade.getInstance().getRateListFromSubject(State.getInstance().getSelectedBottle().getId());
            rateList.forEach(rate -> cardList.add(new RateCard(rate, this)));

            cardHolder.setAlignment(Pos.CENTER);
            cardHolder.setVgap(30.00);
//            cardHolder.setHgap(30.00);
            cardHolder.setStyle("-fx-padding:50px;-fx-alignment: center;");

            onSearch();
        }
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
            cardHolder.add(card, 1, count / nbColumn);
            count++;
        }
    }

    public void onAction() {
        this.initialize(null, null);
    }

    public void formulaireCreation(){
        hbox.getChildren().clear();
        hbox.setAlignment(Pos.CENTER);
        LabelField labelField = new LabelField("Ajouter un commentaire", true);
//        labelField.setStyle("-fx-background-color: lightgrey;");
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

            ObjectId ownerRef = null;
            try {
                ownerRef = Facade.getInstance().getLoggedUser().getId();
                Rate rate = new Rate(ownerRef, State.getInstance().getSelectedBottle().getId(), sliderValue, text, false, new Date());
                Facade.getInstance().insertOneRate(rate);
                labelField.setValue("");
                slider.setValue(1);

                cardList.add(new RateCard(rate, this));
                onSearch();
            } catch (NoLoggedUser e) {
                e.printStackTrace();
            } catch (BadCredentialException e) {
                Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de la création de l'avis", e.getMessage(), Alert.AlertType.ERROR);
                erreur.show();
            }
        });
    }

}
