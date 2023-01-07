package ui.app.page.company.list;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import persistence.entity.company.Company;
import ui.app.component.card.CardComponent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyList implements Initializable {

    @FXML
    private HBox titleHBox;

    @FXML
    private FlowPane listFlowPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listFlowPane.getChildren().clear();

        listFlowPane.setHgap(10);
        listFlowPane.setVgap(10);

        Facade.getInstance().getCompanyList().forEach(company -> {
            listFlowPane.getChildren().add(CardComponent.createCompanyCard(company));
        });
    }
}
