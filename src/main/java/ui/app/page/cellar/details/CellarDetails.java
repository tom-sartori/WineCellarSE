package ui.app.page.cellar.details;

import constant.NodeCreations;
import exception.BadArgumentsException;
import exception.BadCredentialException;
import exception.NotFoundException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import persistence.entity.advertising.Advertising;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.bottle.create.CreateBottleForm;
import ui.app.page.bottle.details.BottleDetails;
import ui.app.page.cellar.forms.addwallform.AddWallForm;
import ui.app.page.cellar.forms.update.UpdateCellarForm;
import ui.app.page.cellar.lists.cellarbyuser.CellarByUser;
import ui.app.page.company.advertising.AdvertisingCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CellarDetails implements Initializable {

    @FXML
    private BorderPane scrollableBorderPane;

    @FXML
    private Label cellarName;

    private Cellar currentCellar;

    private VBox mainVBox;

    private List<Cellar> cellarsFromUser;

    private boolean seeAllBottles = false;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    private boolean isOwner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainVBox = new VBox();
        refresh();
    }

    public void refresh(){
        mainVBox.getChildren().clear();

        if (State.getInstance().getSelectedCellar() != null) {
            currentCellar = Facade.getInstance().getOneCellar(State.getInstance().getSelectedCellar().getId());
            isOwner = State.getInstance().getSelectedCellar() != null && Facade.getInstance().isManagerOfCellar(State.getInstance().getSelectedCellar().getId());
        }

        // Mise à jour du cellar courant.

        if (isOwner){
            try{
                cellarsFromUser = Facade.getInstance().getCellarsFromUser(State.getInstance().getCurrentUser().getId());
            }
            catch (Exception e){
                cellarsFromUser = new ArrayList<>();
            }
        }
        scrollableBorderPane.getChildren().clear();

        createDetailPage();

    }

    public void createDetailPage(){
        scrollableBorderPane.setCenter(null);

        mainVBox.getChildren().clear();

        // Récupérer le label et changer le texte

        if (currentCellar != null) {
            cellarName.setText("Page de la cave: " +  currentCellar.getName());
        }

        // Setup le top de la page

        HBox hbox = new HBox();

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40.0);

        if(isOwner){
            String[] options = new String[cellarsFromUser.size()+1];
            int i = 0;
            while (i < cellarsFromUser.size()){
                options[i] = cellarsFromUser.get(i).getName();
                i++;
            }
            options[i] = "Voir toutes ses bouteilles";
            Select select = new Select("Cave selectionnée", true, options);
            if (seeAllBottles){
                select.getChoiceBox().setValue("Voir toutes ses bouteilles");
            } else {
                select.getChoiceBox().setValue(currentCellar.getName());
            }
            select.getChoiceBox().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() < cellarsFromUser.size()){
                    seeAllBottles = false;
                    State.getInstance().setSelectedCellar(cellarsFromUser.get(newValue.intValue()));

                } else {
                    State.getInstance().setSelectedCellar(null);
                    seeAllBottles = true;
                }
                refresh();
            });
            hbox.getChildren().add(select);
        }
        else{
            if (currentCellar != null) {
                Label label = new Label(currentCellar.getName());
                label.setFont(javafx.scene.text.Font.font("System Bold", 30));
                hbox.getChildren().add(label);
            }
        }

        LabelField labelField = new LabelField("Créer une nouvelle cave", true);

        hbox.getChildren().add(labelField);

        Button createCellarButton = NodeCreations.createButton("Créer");
        createCellarButton.setOnAction(event -> {
            if (!labelField.getValue().equals("")){
                Cellar cellar = new Cellar(labelField.getValue(),false, new ArrayList<>(), new ArrayList<>(),State.getInstance().getCurrentUser().getId(),new ArrayList<>());
                try {
                    Facade.getInstance().insertOneCellar(cellar);
                    State.getInstance().setSelectedCellar(cellar);
                    refresh();
                } catch (BadCredentialException e) {
                    Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de la création de la cave", e.getMessage(), Alert.AlertType.ERROR);
                    erreur.showAndWait();
                }
            }
            else{
                NodeCreations.createAlert("Erreur", "Erreur lors de la création de la cave", "Entrez un nom valide pour votre cave !", Alert.AlertType.ERROR);
            }

        });

        hbox.getChildren().add(createCellarButton);

        scrollableBorderPane.setTop(hbox);

        // boutton de mise à jour (dispo seulement si on est le propriétaire)

        if(isOwner){
            HBox cellarParameters = new HBox();

            cellarParameters.setSpacing(15);
            cellarParameters.setAlignment(Pos.CENTER);
            cellarParameters.setPadding(new Insets(15));

            Button addWallButton = new Button("Ajouter un mur");
            addWallButton.setCursor(Cursor.HAND);
            addWallButton.setOnAction(event -> {
                sceneHelper.bringNodeToFront(AddWallForm.class.getSimpleName());
            });
            cellarParameters.getChildren().add(addWallButton);

            Button updateButton = new Button("Modifier la cave");
            updateButton.setCursor(Cursor.HAND);
            updateButton.setOnAction(event -> {
                sceneHelper.bringNodeToFront(UpdateCellarForm.class.getSimpleName());
            });

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            cellarParameters.getChildren().add(region);

            cellarParameters.getChildren().add(updateButton);

            Button deleteButton = new Button("Supprimer la cave");
            deleteButton.setCursor(Cursor.HAND);
            deleteButton.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText("Sample Alert");
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.get() == ButtonType.OK) {
                    Facade.getInstance().deleteOneCellar(currentCellar.getId());
                    sceneHelper.bringNodeToFront(CellarByUser.class.getSimpleName());
                }
            });
            cellarParameters.getChildren().add(deleteButton);

            mainVBox.getChildren().add(cellarParameters);
        }

        // create table

        if (seeAllBottles && isOwner) {
            List<BottleQuantity> bottleListFromUser = new ArrayList<>();
            for (Cellar cellar : cellarsFromUser) {
                cellar.getWalls().forEach(wall -> {
                    wall.getEmplacementBottleMap().forEach((emplacement) -> {
                        bottleListFromUser.addAll(emplacement.getBottleList());
                    });
                });
            }

            ArrayList<String> tableHeaders = new ArrayList<>();
            tableHeaders.add("Nom");
            tableHeaders.add("Catégorie");
            tableHeaders.add("Producteur");
            tableHeaders.add("Quantité");

            TableView<List<String>> table = NodeCreations.createTable(tableHeaders);

            for (BottleQuantity bottle : bottleListFromUser) {
                List<String> row = new ArrayList<>();
                row.add(bottle.getBottle().getBottleName());
                row.add(bottle.getBottle().getCategory());
                row.add(bottle.getBottle().getProducer());
                row.add(String.valueOf(bottle.getQuantity()));

                table.getItems().add(row);

            }

            mainVBox.getChildren().add(table);
        } else {
            if (currentCellar != null) {
                createAndAddCurrentTable();
            }
        }

        scrollableBorderPane.setCenter(mainVBox);
    }

    public void createAndAddCurrentTable(){

        // Creating table header

        ArrayList<String> tableHeaders = new ArrayList<>();
        tableHeaders.add("Nom");
        tableHeaders.add("Catégorie");
        tableHeaders.add("Producteur");
        tableHeaders.add("Quantité");

        for (Wall wall: currentCellar.getWalls()) {

            HBox wallParams = new HBox();

            wallParams.setAlignment(Pos.CENTER);
            wallParams.setSpacing(15);
            wallParams.setPadding(new Insets(15));

            Label labelWall = new Label("Mur: " + wall.getName());
            labelWall.setFont(javafx.scene.text.Font.font("System Bold", 25));
            wallParams.getChildren().add(labelWall);

            Region region1 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);

            wallParams.getChildren().add(region1);

            if (isOwner) {
                Button addEmplacementButton = new Button("Ajouter un emplacement");
                addEmplacementButton.setCursor(Cursor.HAND);
                addEmplacementButton.setOnAction(event -> {
                    try {
                        State.getInstance().setSelectedCellar(Facade.getInstance().getOneCellar(State.getInstance().getSelectedCellar().getId()));
                        Facade.getInstance().addEmplacement(State.getInstance().getSelectedCellar(), wall, new EmplacementBottle(new ArrayList<>(), new ArrayList<>()));
                    } catch (BadArgumentsException e) {
                        Alert erreur = NodeCreations.createAlert("Erreur", "Erreur lors de l'ajout de l'emplacement", e.getMessage(), Alert.AlertType.ERROR);
                        erreur.showAndWait();

                    }
                    refresh();
                });
                wallParams.getChildren().add(addEmplacementButton);

                Button deleteWallButton = NodeCreations.createButton("Supprimer le mur");
                deleteWallButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setContentText("Sample Alert");
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get() == ButtonType.OK) {
                        try {
                            State.getInstance().setSelectedCellar(Facade.getInstance().getOneCellar(State.getInstance().getSelectedCellar().getId()));
                            Facade.getInstance().removeWall(wall, State.getInstance().getSelectedCellar().getId());
                        } catch (BadArgumentsException e) {
                            NodeCreations.createAlert("Erreur", "Erreur lors de la suppression du mur", e.getMessage(),Alert.AlertType.ERROR);
                        }
                        refresh();
                    }
                });
                wallParams.getChildren().add(deleteWallButton);
            }

            mainVBox.getChildren().add(wallParams);

            for (EmplacementBottle emplacementBottle: wall.getEmplacementBottleMap()) {

                HBox emplacementHBox = new HBox();

                emplacementHBox.setAlignment(Pos.CENTER);
                emplacementHBox.setSpacing(15);

                emplacementHBox.setPadding(new Insets(15));

                Label labelEmplacement = new Label("Emplacement n°" + (wall.getEmplacementBottleMap().indexOf(emplacementBottle) + 1));
                labelEmplacement.setFont(javafx.scene.text.Font.font("System Bold", 20));
                emplacementHBox.getChildren().add(labelEmplacement);

                if (isOwner) {
                    Button addBottleButton = NodeCreations.createButton("Ajouter une bouteille");
                    addBottleButton.setOnAction(event -> {
                        State.getInstance().setSelectedCellar(currentCellar);
                        State.getInstance().setSelectedEmplacementBottle(emplacementBottle);
                        State.getInstance().setSelectedWall(wall);
                        sceneHelper.bringNodeToFront(CreateBottleForm.class.getSimpleName());
                    });
                    emplacementHBox.getChildren().add(addBottleButton);

                    Region regionEmplacement = new Region();
                    HBox.setHgrow(regionEmplacement, Priority.ALWAYS);

                    emplacementHBox.getChildren().add(regionEmplacement);

                    Button deleteEmplacementButton = new Button("Supprimer l'emplacement");
                    deleteEmplacementButton.setCursor(Cursor.HAND);
                    deleteEmplacementButton.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setContentText("Sample Alert");
                        Optional<ButtonType> buttonType = alert.showAndWait();

                        if (buttonType.get() == ButtonType.OK) {
                            try {
                                State.getInstance().setSelectedCellar(Facade.getInstance().getOneCellar(State.getInstance().getSelectedCellar().getId()));
                                Facade.getInstance().removeEmplacement(State.getInstance().getSelectedCellar(), wall, emplacementBottle);
                            } catch (BadArgumentsException e) {
                                NodeCreations.createAlert("Erreur", "Erreur lors de la suppression de l'emplacement", e.getMessage(),Alert.AlertType.ERROR);
                            }
                            refresh();
                        }
                    });
                    emplacementHBox.getChildren().add(deleteEmplacementButton);
                }

                mainVBox.getChildren().add(emplacementHBox);

                HBox tableActionsHbox = new HBox();

                tableActionsHbox.setPrefWidth(1260);

                TableView<List<String>> table = NodeCreations.createTable(tableHeaders);
                table.setPrefWidth(1200);

                VBox actionsVbox = new VBox();
                actionsVbox.setPrefWidth(60);

                Label actions = new Label("Actions");
                actions.setPadding(new Insets(5, 0, 5, 5));
                actionsVbox.getChildren().add(actions);

                tableActionsHbox.getChildren().addAll(table, actionsVbox);

                for (BottleQuantity bottleQuantity: emplacementBottle.getBottleList()) {

                    ArrayList<String> bottle = new ArrayList<>();
                    bottle.add(bottleQuantity.getBottle().getBottleName());
                    bottle.add(bottleQuantity.getBottle().getCategory());
                    bottle.add(bottleQuantity.getBottle().getProducer());
                    bottle.add(String.valueOf(bottleQuantity.getQuantity()));

                    table.getItems().add(bottle);

                    HBox actionsHbox = new HBox();

                    // TODO check to put icon instead of button
                    Button detailButton = NodeCreations.createButton("D");
                    detailButton.setOnAction(event -> {
                        State.getInstance().setSelectedCellar(currentCellar);
                        State.getInstance().setSelectedWall(wall);
                        State.getInstance().setSelectedEmplacementBottle(emplacementBottle);
                        State.getInstance().setSelectedBottle(bottleQuantity.getBottle());
                        sceneHelper.bringNodeToFront(BottleDetails.class.getSimpleName());
                    });
                    actionsHbox.getChildren().add(detailButton);

                    if(isOwner){
                        Button deleteButton2 = new Button("Supprimer");
                        deleteButton2.setCursor(Cursor.HAND);

                        Button addButton = new Button("+");
                        addButton.setCursor(Cursor.HAND);
                        addButton.setOnAction(event -> {
                            try {
                                Facade.getInstance().increaseBottleQuantity(currentCellar,wall,emplacementBottle,bottleQuantity);
                            } catch (BadArgumentsException e) {
                                NodeCreations.createAlert("Erreur", "Erreur lors de l'ajout de bouteille", e.getMessage(),Alert.AlertType.ERROR);
                            }
                            refresh();
                        });
                        Button removeButton;
                        if (bottleQuantity.getQuantity() == 0) {
                            removeButton = new Button("x");
                        }
                        else{
                            removeButton = new Button("-");
                        }

                        removeButton.setCursor(Cursor.HAND);
                        removeButton.setOnAction(event -> {
                            try {
                                Facade.getInstance().decreaseBottleQuantity(currentCellar,wall,emplacementBottle,bottleQuantity);
                                refresh();
                            } catch (BadArgumentsException e) {
                                NodeCreations.createAlert("Erreur", "Erreur lors de la suppression de bouteille", e.getMessage(),Alert.AlertType.ERROR);
                            }
                        });

                        actionsHbox.getChildren().addAll(addButton, removeButton);
                    }
                    actionsVbox.getChildren().add(actionsHbox);
                }
                mainVBox.getChildren().add(tableActionsHbox);
            }

            try{
                Advertising randomAdvertising = Facade.getInstance().getRandomAdvertising();

                HBox advertisingBox = new HBox();
                advertisingBox.setPadding(new Insets(20));
                advertisingBox.setAlignment(Pos.CENTER);
                advertisingBox.getChildren().add(new AdvertisingCard(randomAdvertising, "cellarDetails"));

                mainVBox.getChildren().add(advertisingBox);
            }catch (NotFoundException ignored){
                // do nothing
            }

        }
    }

}
