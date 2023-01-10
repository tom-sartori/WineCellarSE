package ui.app.page.cellar.lists.cellarbyuser;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import persistence.entity.cellar.Cellar;
import ui.app.State;
import ui.app.component.card.CardComponent;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.cellar.forms.create.CellarForm;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CellarByUser implements Initializable {

    @FXML
    private FlowPane cardHolder;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshPage();
    }

    public void refreshPage(){
        cardHolder.getChildren().clear();

        cardHolder.setHgap(10.00);
        cardHolder.setVgap(10.00);

        if(State.getInstance().getCurrentUser() != null){
            List<Cellar> cellarList = Facade.getInstance().getCellarsFromUser(State.getInstance().getCurrentUser().getId());
            for (Cellar cellar : cellarList) {
                CardComponent card = CardComponent.createCellarCard(cellar);
                cardHolder.getChildren().add(card);
            }
        }
    }

    public void redirectToCreateCellar(){
        sceneHelper.bringNodeToFront(CellarForm.class.getSimpleName());
    }
}
