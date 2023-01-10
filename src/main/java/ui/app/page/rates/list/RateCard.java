package ui.app.page.rates.list;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import persistence.entity.rate.Rate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Optional;

public class RateCard extends Pane {
    private Rate rate;
    private RateList rateList;

    @FXML
    private AnchorPane rateCard;

    @FXML
    private Button supprimer;

    private Label username;
    private Label note;

    public RateCard(Rate rate, RateList rateList){
        this.rate = rate;
        this.rateList = rateList;
        username = new Label();

        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center; -fx-border-width: 1px; -fx-padding:5px;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);

        textArea.setEditable(false);
        textArea.setText(rate.getComment());
        textArea.setLayoutX(85.0);
        textArea.setLayoutY(7.0);
        textArea.setPrefHeight(45.0);
        textArea.setPrefWidth(170.0);

        username.setText(Facade.getInstance().getOneUser(rate.getOwnerRef()).getUsername());
        username.setLayoutX(262.0);
        username.setLayoutY(17.0);
        username.setPrefHeight(17.0);
        username.setPrefWidth(70.0);

        GridPane starsGrid = new GridPane();
        starsGrid.setLayoutX(8.0);
        starsGrid.setLayoutY(17.0);

        for (int i = 1; i <= rate.getRate() ; i++) {
            ImageView star = new ImageView();
            try {
                star.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/star.png")).getPath())));
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            int photoSize = 15;
            star.setFitHeight(photoSize);
            star.setFitWidth(photoSize);

            starsGrid.add(star, i, 1);
        }

        getChildren().addAll(textArea, username, starsGrid);

        if (Facade.getInstance().isAdminLogged()){
            ImageView trash = new ImageView();
            try {
                trash.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/trash.png")).getPath())));
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            int photoSize = 20;
            trash.setFitHeight(photoSize);
            trash.setFitWidth(photoSize);
            trash.setLayoutX(330.0);
            trash.setLayoutY(17.0);
            getChildren().add(trash);

            trash.setOnMouseClicked(e -> {
                supprimerRate();
            });
        }
        try{
            if(Facade.getInstance().getLoggedUser().getId().equals(rate.getOwnerRef())){
                ImageView trash = new ImageView();
                try {
                    trash.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/trash.png")).getPath())));
                }
                catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                int photoSize = 20;
                trash.setFitHeight(photoSize);
                trash.setFitWidth(photoSize);
                trash.setLayoutX(330.0);
                trash.setLayoutY(17.0);
                getChildren().add(trash);

                trash.setOnMouseClicked(e -> {
                    supprimerRate();
                });
            }
        }catch(NoLoggedUser ignore){ }


    }

    public RateList getRateList() {
        return rateList;
    }

    public void setRateList(RateList rateList) {
        this.rateList = rateList;
    }

    public void supprimerRate(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cet avis ?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() != ButtonType.CANCEL){
            rateList.getCardList().remove(this);
            Facade.getInstance().deleteOneRate(rate.getId());
            rateList.onSearch();

        }
    }


}
