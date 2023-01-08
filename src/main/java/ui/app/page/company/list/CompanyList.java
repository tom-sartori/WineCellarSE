package ui.app.page.company.list;

import constant.NodeCreations;
import exception.NotFoundException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import ui.app.component.card.CardComponent;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.form.create.CompanyCreate;

import java.net.URL;
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
        titleHBox.getChildren().clear();

        try {
            if (Facade.getInstance().getLoggedUser() != null) {
                Button createCompany = NodeCreations.createButton("Créer une entreprise");

                createCompany.setOnAction(event -> {
                    CustomSceneHelper sceneHelper = new CustomSceneHelper();
                    sceneHelper.bringNodeToFront(CompanyCreate.class.getSimpleName());
                });

                titleHBox.getChildren().add(createCompany);
            }
        } catch (NoLoggedUser e) {
            // do nothing
        }

        listFlowPane.getChildren().clear();

        listFlowPane.setHgap(10);
        listFlowPane.setVgap(10);

        try{
            Facade.getInstance().findAllAccessibleCompanies().forEach(company -> {
                // TODO trier par ça
//                System.out.println(Facade.getInstance().getReferencingsByCompanyByStatus(company.getId(),"En cours").toString());
                listFlowPane.getChildren().add(CardComponent.createCompanyCard(company));
            });
        }catch (NotFoundException e){
            // do nothing
        }
    }
}
