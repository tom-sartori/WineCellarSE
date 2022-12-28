package ui.app.page.cellar.publiccellars;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.card.CardComponent;
import ui.app.page.cellar.details.CellarDetails;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PublicCellars implements Initializable {

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

        List<Cellar> cellarList = Facade.getInstance().getPublicCellars();

        for (Cellar cellar : cellarList) {
            createCellarCard(cellar);
        }
    }

    // TODO HANDLE WHEN OWNER NOT FOUND
    public void createCellarCard(Cellar cellar){
        try{
            CardComponent card = new CardComponent();
            card.setText(cellar.getName());
            card.addNode(new Label("Public: " + cellar.isPublic()));

            User oneUser = Facade.getInstance().getOneUser(cellar.getOwnerRef());

            card.addNode(new Label("Propriétaire: " + oneUser.getUsername()));

            Button button = new Button("Voir");
            button.onActionProperty().set(event -> {
                State.getInstance().setSelectedCellar(cellar);
                CustomSceneHelper sceneHelper = new CustomSceneHelper();
                sceneHelper.bringNodeToFront(CellarDetails.class.getSimpleName());
            });

            card.addBottomNode(button);

            cardHolder.getChildren().add(card);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
