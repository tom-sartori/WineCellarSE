package ui.app.page.rates.list;

import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import persistence.entity.rate.Rate;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class RateCard extends Pane {
    private Rate rate;

    @FXML
    private AnchorPane rateCard;

    @FXML
    private Button supprimer;


    private TextFlow commentaireFlow;
    private Label username;
    private Label note;
    protected final Button boutonSuppression;




    public RateCard(Rate rate){
        this.rate = rate;
        this.commentaireFlow = new TextFlow();
        username = new Label();
        note = new Label();
        boutonSuppression = new Button("x");
        boutonSuppression.setLayoutX(330.0);
        boutonSuppression.setLayoutY(17.0);

        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center; -fx-border-width: 1px");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);

        try{


            String commentaire = rate.getComment();

            Text commentaireText = new Text(commentaire);
            commentaireText.setWrappingWidth(120.0);


            commentaireFlow.getChildren().add(commentaireText);


            commentaireFlow.setLayoutX(85.0);
            commentaireFlow.setLayoutY(17.0);
            commentaireFlow.setPrefHeight(40.0);
            commentaireFlow.setPrefWidth(120.0);


            username.setText(Facade.getInstance().getOneUser(rate.getOwnerRef()).getUsername());
            username.setLayoutX(258.0);
            username.setLayoutY(17.0);
            username.setPrefHeight(17.0);
            username.setPrefWidth(70.0);

            note.setText("Note : "+ rate.getRate());
            note.setLayoutX(14.0);
            note.setLayoutY(17.0);
            note.setPrefHeight(17.0);
            note.setPrefWidth(76.0);

            getChildren().addAll(commentaireFlow, username, note, boutonSuppression);

        }catch(Exception e){
        e.printStackTrace();
        }

        boutonSuppression.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Voulez-vous vraiment supprimer cet avis ?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get() != ButtonType.CANCEL){
                    Facade.getInstance().deleteOneRate(rate.getId());
                }
            }
        });


    }
}
