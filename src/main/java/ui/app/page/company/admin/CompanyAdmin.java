package ui.app.page.company.admin;

import constant.NodeCreations;
import exception.NotFoundException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import ui.app.component.card.CardComponent;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.form.create.CompanyCreate;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyAdmin implements Initializable {

    @FXML
    private HBox titleHBox;

    @FXML
    private FlowPane listFlowPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleHBox.getChildren().clear();

        titleHBox.getChildren().add(new Label("Voir les requêtes d'adhésion d'entreprises"));

        listFlowPane.getChildren().clear();

        listFlowPane.setHgap(10);
        listFlowPane.setVgap(10);

        try{
            Facade.getInstance().findAllUnaccessibleCompanies().forEach(company -> {
                listFlowPane.getChildren().add(CardComponent.createCompanyRequestCard(company));
            });
        }catch (NotFoundException e){
            // do nothing
        }
    }
}
