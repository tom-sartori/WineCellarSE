package ui.app.page.cellar.lists.shared;

import constant.NodeCreations;
import exception.NotFoundException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;
import ui.app.component.card.CardComponent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SharedWithMeCellars implements Initializable {

    @FXML
    private VBox vBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vBox.getChildren().clear();
        User loggedUser = null;
        try {
            loggedUser = Facade.getInstance().getLoggedUser();
        } catch (NoLoggedUser e) {
            NodeCreations.createAlert("Aucun utilisateur connecté", "Vous devez vous connecter pour voir vos caves", e.getMessage(), Alert.AlertType.ERROR);
        }

        List<Cellar> cellarsWhereUserIsManager = new ArrayList<>();
        List<Cellar> readOnlyCellarsFromUser = new ArrayList<>();
        if (loggedUser != null) {
            try {
                cellarsWhereUserIsManager = Facade.getInstance().getCellarsWhereUserIsManager(loggedUser.getId());
            }
            catch (NotFoundException ignored){
            }

            try {
                readOnlyCellarsFromUser = Facade.getInstance().getReadOnlyCellarsFromUser(loggedUser.getId());
            }
            catch (NotFoundException ignored){

            }

            vBox.getChildren().add(new Label("Caves partagées en tant que gestionnaire"));

            FlowPane cardHolderForManagerCellars = new FlowPane();

            for (Cellar cellar : cellarsWhereUserIsManager) {
                CardComponent card = CardComponent.createCellarCard(cellar);
                cardHolderForManagerCellars.getChildren().add(card);
            }

            vBox.getChildren().add(cardHolderForManagerCellars);

            vBox.getChildren().add(new Label("Caves partagées en tant que lecteur"));

            FlowPane cardHolderForReadOnlyCellars = new FlowPane();

            for (Cellar cellar : readOnlyCellarsFromUser) {
                CardComponent card = CardComponent.createCellarCard(cellar);
                cardHolderForReadOnlyCellars.getChildren().add(card);
            }

            vBox.getChildren().add(cardHolderForReadOnlyCellars);
        }



    }
}
