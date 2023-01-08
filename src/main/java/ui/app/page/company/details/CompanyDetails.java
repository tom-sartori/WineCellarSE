package ui.app.page.company.details;

import constant.NodeCreations;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistence.entity.company.Company;
import ui.app.State;
import ui.app.component.card.CardComponent;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class CompanyDetails implements Initializable {

    @FXML
    private AnchorPane titlePaneCreateBottle;

    @FXML
    private AnchorPane mainPaneCreateBottle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneCreateBottle.getChildren().add(new Label("Détails d'une entreprise"));

        if (State.getInstance().getSelectedCompany() != null) {
            Company company = State.getInstance().getSelectedCompany();

            VBox vBox = new VBox();

            vBox.setPrefWidth(1260);
            vBox.setAlignment(javafx.geometry.Pos.CENTER);
            vBox.setSpacing(10.0);

            HBox firstRow = new HBox();

            firstRow.setPrefWidth(1260);
            firstRow.setAlignment(javafx.geometry.Pos.CENTER);
            firstRow.setSpacing(10.0);

            VBox logoAndAdress = new VBox();
            logoAndAdress.getChildren().add(new Label("Logo: " + company.getLogoLink()));
            logoAndAdress.getChildren().add(new Label("Adresse: " + company.getAddress()));

            VBox nameAndType = new VBox();
            nameAndType.getChildren().add(new Label("Nom de la société: " + company.getName()));
            nameAndType.getChildren().add(new Label("Type entreprise: " + company.getType()));

            firstRow.getChildren().add(logoAndAdress);
            firstRow.getChildren().add(nameAndType);

            firstRow.getChildren().add(new Label("Master manager : " + company.getMasterManager().toString()));

            vBox.getChildren().add(firstRow);

            vBox.getChildren().add(new Label("Description : " + company.getDescription()));

            HBox thirdRow = new HBox();

            thirdRow.getChildren().add(CardComponent.createCellarCard(company.getCellar()));

            // TODO add rates on third row

            vBox.getChildren().add(thirdRow);

            HBox fourthRow = new HBox();

            fourthRow.getChildren().add(NodeCreations.createButton("Supprimer"));
            fourthRow.getChildren().add(NodeCreations.createButton("Modifier"));

            vBox.getChildren().add(fourthRow);

            HBox fifthRow = new HBox();

            fifthRow.getChildren().add(new Label("Téléphone : " + company.getPhoneNumber()));
            fifthRow.getChildren().add(new Label("Site web : " + company.getWebsiteLink()));

            vBox.getChildren().add(fifthRow);

            mainPaneCreateBottle.getChildren().add(vBox);


        }
    }

}
