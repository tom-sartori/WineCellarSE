package ui.app.page.company.details;

import constant.NodeCreations;
import exception.BadArgumentsException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import persistence.entity.cellar.Cellar;
import persistence.entity.company.Company;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.component.card.CardComponent;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.company.advertising.creation.AdvertisingCreation;
import ui.app.page.company.advertising.list.AdvertisingList;
import ui.app.page.company.event.EventCard;
import ui.app.page.company.event.creation.EventCreation;
import ui.app.page.company.form.update.CompanyUpdate;
import ui.app.page.company.list.CompanyList;
import ui.app.page.company.referencing.creation.ReferencingCreation;
import ui.app.page.company.referencing.list.ReferencingList;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyDetails implements Initializable {

    @FXML
    private AnchorPane titlePaneCreateBottle;

    @FXML
    private AnchorPane mainPaneCreateBottle;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlePaneCreateBottle.getChildren().clear();
        mainPaneCreateBottle.getChildren().clear();

        HBox title = new HBox();

        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(1280);

        Label label1 = new Label("Détails d'une entreprise");
        label1.setFont(new Font(45));
        title.getChildren().add(label1);

        titlePaneCreateBottle.getChildren().add(title);

        if (State.getInstance().getSelectedCompany() != null) {
            State.getInstance().setSelectedCompany(Facade.getInstance().getOneCompany(State.getInstance().getSelectedCompany().getId()));

            Company company = State.getInstance().getSelectedCompany();

            VBox vBox = new VBox();

            vBox.setPrefWidth(1260);
            vBox.setAlignment(javafx.geometry.Pos.CENTER);
            vBox.setSpacing(10.0);

            HBox firstRow = new HBox();

            Region regionFirstRowFirst = new Region();
            HBox.setHgrow(regionFirstRowFirst, Priority.ALWAYS);

            firstRow.getChildren().add(regionFirstRowFirst);

            firstRow.setPadding(new Insets(40));
            firstRow.setSpacing(20);

            firstRow.setPrefWidth(1260);
            firstRow.setAlignment(javafx.geometry.Pos.CENTER);
            firstRow.setSpacing(10.0);

            Label labelAdress = new Label(company.getAddress());

            labelAdress.setWrapText(true);

            firstRow.getChildren().add(labelAdress);

            VBox nameAndType = new VBox();

            nameAndType.setAlignment(Pos.TOP_CENTER);

            Label labelCompanyName = new Label(company.getName());
            labelCompanyName.setFont(new Font(40));

            nameAndType.getChildren().add(labelCompanyName);

            Label labelTypeCompany = new Label(company.getType());
            labelTypeCompany.setFont(new Font(30));
            nameAndType.getChildren().add(labelTypeCompany);

            Region regionFirstRowSecond = new Region();
            HBox.setHgrow(regionFirstRowSecond, Priority.ALWAYS);

            firstRow.getChildren().add(regionFirstRowSecond);

            firstRow.getChildren().add(nameAndType);

            User masterManager = Facade.getInstance().getOneUser(company.getMasterManager());

            Region regionFirstRowThird = new Region();
            HBox.setHgrow(regionFirstRowThird, Priority.ALWAYS);

            firstRow.getChildren().add(regionFirstRowThird);

            firstRow.getChildren().add(new Label("Gérant : " + masterManager.getUsername()));

            Region regionFirstRowFourth = new Region();
            HBox.setHgrow(regionFirstRowFourth, Priority.ALWAYS);

            firstRow.getChildren().add(regionFirstRowFourth);

            vBox.getChildren().add(firstRow);

            HBox secondRow = new HBox();

            secondRow.setAlignment(Pos.TOP_CENTER);

            VBox vBox1 = new VBox();

            vBox1.setSpacing(15);
            vBox1.setMaxWidth(300);

            vBox1.getChildren().add(new Label("Description"));
            Label label = new Label(company.getDescription());
            label.setWrapText(true);
            vBox1.getChildren().add(label);

            secondRow.getChildren().add(vBox1);

            try{
                if (company.getFollowerList().contains(Facade.getInstance().getLoggedUser().getId())){
                    Button unfollowButton = NodeCreations.createButton("Se désabonner");
                    unfollowButton.setOnAction(event -> {
                        try {
                            Facade.getInstance().unfollowCompany(company.getId(), Facade.getInstance().getLoggedUser().getId());
                            initialize(null,null);
                        } catch (BadArgumentsException | NoLoggedUser e) {
                            Alert erreur = NodeCreations.createAlert("Erreur", "Une erreur est survenu lors de votre désabonnement", e.getMessage(), Alert.AlertType.ERROR);
                            erreur.showAndWait();
                        }
                    });
                    secondRow.getChildren().add(unfollowButton);
                } else {
                    Button followButton = NodeCreations.createButton("S'abonner");
                    followButton.setOnAction(event -> {
                        try {
                            Facade.getInstance().followCompany(company.getId(), Facade.getInstance().getLoggedUser().getId());
                            initialize(null,null);
                        } catch (BadArgumentsException | NoLoggedUser e) {
                            Alert erreur = NodeCreations.createAlert("Erreur", "Une erreur est survenu lors de votre désabonnement", e.getMessage(), Alert.AlertType.ERROR);
                            erreur.showAndWait();
                        }
                    });
                    secondRow.getChildren().add(followButton);
                }
            } catch (NoLoggedUser e) {
                // do nothing
            }

            vBox.getChildren().add(secondRow);

            HBox thirdRow = new HBox();

            thirdRow.setAlignment(Pos.CENTER);
            thirdRow.setPadding(new Insets(30));

            Cellar oneCellar = Facade.getInstance().getOneCellar(company.getCellar());
            thirdRow.getChildren().add(CardComponent.createCellarCard(oneCellar));

            // TODO add rates on third row

            vBox.getChildren().add(thirdRow);

            HBox fourthRow = new HBox();

            fourthRow.setAlignment(Pos.CENTER);
            fourthRow.setSpacing(50);
            fourthRow.setPadding(new Insets(20));

            if (Facade.getInstance().isManagerOfCompany(company.getId())){

                Button modifier = NodeCreations.createButton("Modifier");
                modifier.setOnAction(event -> {
                    State.getInstance().setSelectedCompany(company);
                    sceneHelper.bringNodeToFront(CompanyUpdate.class.getSimpleName());
                });
                fourthRow.getChildren().add(modifier);

                Button deleteButton = NodeCreations.createButton("Supprimer");
                deleteButton.setOnAction(event -> {
                    Alert alert = NodeCreations.createAlert("Suppression d'une entreprise", "Confirmation de suppression","Êtes-vous sûr de vouloir supprimer cette entreprise ?", Alert.AlertType.CONFIRMATION);
                    alert.showAndWait();
                    if (alert.getResult().getText().equals("OK")){
                        Facade.getInstance().deleteOneCompany(company.getId());
                        State.getInstance().setSelectedCompany(null);
                        sceneHelper.bringNodeToFront(CompanyList.class.getSimpleName());
                    }
                });
                fourthRow.getChildren().add(deleteButton);

                vBox.getChildren().add(fourthRow);
            }

            HBox fifthRow = new HBox();

            fifthRow.setPadding(new Insets(15));

            fifthRow.getChildren().add(new Label("Téléphone : " + company.getPhoneNumber()));

            Region regionBetweenTelephoneAndWebSite = new Region();
            HBox.setHgrow(regionBetweenTelephoneAndWebSite, Priority.ALWAYS);

            fifthRow.getChildren().add(regionBetweenTelephoneAndWebSite);

            fifthRow.getChildren().add(new Label("Site web : " + company.getWebsiteLink()));

            vBox.getChildren().add(fifthRow);

            if (Facade.getInstance().isManagerOfCompany(company.getId())){
                HBox sixthRow = new HBox();

                Button createEvent = NodeCreations.createButton("Créer un événement");
                createEvent.setOnAction(event -> {
                    sceneHelper.bringNodeToFront(EventCreation.class.getSimpleName());
                });
                Button createAdvertising = NodeCreations.createButton("Créer une publicité");
                createAdvertising.setOnAction(event -> {
                    sceneHelper.bringNodeToFront(AdvertisingCreation.class.getSimpleName());
                });
                Button createReferencing = NodeCreations.createButton("Créer une référencement");
                createReferencing.setOnAction(event -> {
                    sceneHelper.bringNodeToFront(ReferencingCreation.class.getSimpleName());
                });

                sixthRow.getChildren().add(createEvent);
                sixthRow.getChildren().add(createAdvertising);
                sixthRow.getChildren().add(createReferencing);

                vBox.getChildren().add(sixthRow);
            }



            if (Facade.getInstance().isManagerOfCompany(company.getId())){
                HBox seventhRow = new HBox();

                Button seeCompanyAdvertisings = NodeCreations.createButton("Voir les publicités");
                seeCompanyAdvertisings.setOnAction(event -> {
                    sceneHelper.bringNodeToFront(AdvertisingList.class.getSimpleName());
                });

                Button seeCompanyReferencings = NodeCreations.createButton("Voir les référencements");
                seeCompanyReferencings.setOnAction(event -> {
                    sceneHelper.bringNodeToFront(ReferencingList.class.getSimpleName());
                });

                seventhRow.getChildren().add(seeCompanyAdvertisings);
                seventhRow.getChildren().add(seeCompanyReferencings);

                vBox.getChildren().add(seventhRow);
            }

            vBox.getChildren().add(new Label("Événements de l'entreprise : "));
            FlowPane flowPane = new FlowPane();

            try{
                Facade.getInstance().getEventsByCompany(company.getId()).forEach(event -> {
                    flowPane.getChildren().add(new EventCard(event));
                });
            } catch (Exception e) {
                // do nothing
            }


            vBox.getChildren().add(flowPane);

            mainPaneCreateBottle.getChildren().add(vBox);


        }
    }

}
