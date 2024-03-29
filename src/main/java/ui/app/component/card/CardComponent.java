package ui.app.component.card;

import constant.NodeCreations;
import facade.Facade;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import persistence.entity.cellar.Cellar;
import persistence.entity.company.Company;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.cellar.details.CellarDetails;
import ui.app.page.company.admin.CompanyAdmin;
import ui.app.page.company.details.CompanyDetails;

import java.io.IOException;

public class CardComponent extends BorderPane {
    @FXML private Label label;

    @FXML private VBox centerBox;

    @FXML private FlowPane topFlowPane;

    @FXML private FlowPane bottomFlowPane;

    private static final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    public CardComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            topFlowPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            centerBox = new VBox();
            centerBox.setSpacing(2);
            centerBox.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
            setCenter(centerBox);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return label.textProperty();
    }

    public void addNode(Node node){
        centerBox.getChildren().add(node);
    }

    public void addBottomNode(Node node){
        bottomFlowPane.getChildren().add(node);
    }

    /**
     * Creates a card for a cellar
     *
     * @param cellar the cellar to create a card for.
     *
     * @return the card for the cellar.
     */
    public static CardComponent createCellarCard(Cellar cellar){
        CardComponent card = new CardComponent();
        try{
            card.setText(cellar.getName());
            card.addNode(new Label("Public: " + cellar.isPublic()));

            User oneUser = Facade.getInstance().getOneUser(cellar.getOwnerRef());

            card.addNode(new Label("Propriétaire: " + oneUser.getUsername()));

            Button button = NodeCreations.createButton("Voir");
            button.onActionProperty().set(event -> {
                State.getInstance().setSelectedCellar(cellar);
                CustomSceneHelper sceneHelper = new CustomSceneHelper();
                sceneHelper.bringNodeToFront(CellarDetails.class.getSimpleName());
            });

            card.addBottomNode(button);
        }catch (Exception e){
            Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de la création de la carte d'une cave", e.getMessage(), Alert.AlertType.ERROR);
            erreur.show();
        }
        return card;
    }

    /**
     * Creates a card for a company
     *
     * @param company the company to create a card for.
     *
     * @return the card for the company.
     */
    public static CardComponent createCompanyCard(Company company){
        CardComponent card = new CardComponent();
        try{
            card.setText(company.getName());
            card.addNode(new Label("Type de l'entreprise: " + company.getType()));

            Label label1 = new Label("Adresse: " + company.getAddress());
            label1.setWrapText(true);
            card.addNode(label1);

            Button button = NodeCreations.createButton("Voir");
            button.onActionProperty().set(event -> {
                State.getInstance().setSelectedCompany(company);
                CustomSceneHelper sceneHelper = new CustomSceneHelper();
                sceneHelper.bringNodeToFront(CompanyDetails.class.getSimpleName());
            });

            card.addBottomNode(button);
        }catch (Exception e){
            Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de la création de la carte d'une entreprise", e.getMessage(), Alert.AlertType.ERROR);
            erreur.show();
        }
        return card;
    }

    public static CardComponent createCompanyRequestCard(Company company){
        CardComponent companyCard = createCompanyCard(company);

        Button acceptButton = NodeCreations.createButton("Accepter");
        acceptButton.onActionProperty().set(event -> {
            try {
                Facade.getInstance().acceptRequest(company.getId());
                Alert successAlert = NodeCreations.createAlert("Succès", "Demande acceptée", "La demande a été acceptée avec succès", Alert.AlertType.INFORMATION);
                successAlert.showAndWait();
                sceneHelper.bringNodeToFront(CompanyAdmin.class.getSimpleName());
            } catch (Exception e) {
                Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de l'acceptation de la demande", e.getMessage(), Alert.AlertType.ERROR);
                erreur.showAndWait();
            }
        });

        Button refuseButton = NodeCreations.createButton("Refuser");
        refuseButton.onActionProperty().set(event -> {
            try {
                Facade.getInstance().refuseRequest(company.getId());
                Alert success = NodeCreations.createAlert("Succès", "Demande refusée", "La demande a été refusée avec succès", Alert.AlertType.INFORMATION);
                success.showAndWait();
                sceneHelper.bringNodeToFront(CompanyAdmin.class.getSimpleName());
            } catch (Exception e) {
                Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors du refus de la demande", e.getMessage(), Alert.AlertType.ERROR);
                erreur.showAndWait();
            }
        });

        companyCard.topFlowPane.getChildren().add(acceptButton);
        companyCard.topFlowPane.getChildren().add(refuseButton);

        return companyCard;
    }
}
