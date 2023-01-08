package ui.app.page.cellar.lists.friendscellar;

import constant.NodeCreations;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import persistence.entity.cellar.Cellar;
import persistence.entity.user.Friend;
import ui.app.component.card.CardComponent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FriendCellarList implements Initializable {

    @FXML
    private FlowPane cardHolder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardHolder.getChildren().clear();

        cardHolder.setHgap(10.00);
        cardHolder.setVgap(10.00);

        List<Friend> friendList;
        try {
            friendList = Facade.getInstance().getFriendList(true);
        } catch (NoLoggedUser e) {
            friendList = new ArrayList<>();
        }

        ArrayList<Cellar> cellarList = new ArrayList<>();

        if (!friendList.isEmpty()){
            friendList.forEach(friend -> {
                try{
                    cellarList.addAll(Facade.getInstance().getCellarsFromUser(friend.getId()));
                }catch (Exception e){
                    // do nothing
                }
            });
        }

        cellarList.forEach(cellar -> {
            CardComponent card = CardComponent.createCellarCard(cellar);
            cardHolder.getChildren().add(card);
        });
    }
}
