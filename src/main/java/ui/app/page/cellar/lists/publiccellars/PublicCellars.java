package ui.app.page.cellar.lists.publiccellars;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import persistence.entity.cellar.Cellar;
import ui.app.component.card.CardComponent;

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
            CardComponent card = CardComponent.createCellarCard(cellar);
            cardHolder.getChildren().add(card);
        }
    }

}
