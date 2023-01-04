package ui.app.page.cellar.details;

import exception.BadArgumentsException;
import facade.Facade;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.addwallform.AddWallForm;
import ui.app.page.cellar.cellarbyuser.CellarByUser;
import ui.app.page.cellar.createBottle.CreateBottleForm;
import ui.app.page.cellar.details.bottle.BottleDetails;
import ui.app.page.cellar.updatecellar.UpdateCellarForm;

import java.net.URL;
import java.util.*;

// TODO SINGLETON TO BE ABLE TO USE REFRESH METHOD IN OTHER CLASSES ?
// TODO refactor class to separate methods
public class CellarDetails implements Initializable {

    @FXML
    private BorderPane scrollableBorderPane;

    @FXML
    private Label cellarName;

    private Cellar currentCellar;

    private VBox mainVBox;

    private List<Cellar> cellarsFromUser;

    private boolean seeAllBottles = false;

    // TODO Singleton ?!
    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

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
            currentCellar = State.getInstance().getSelectedCellar();

            // TODO est ce qu'on get a chaque fois le cellar ou on update celui dans le state ? (si on update celui dans le state, il faut le faire dans les autres classes)
            currentCellar = Facade.getInstance().getOneCellar(currentCellar.getId());
        }

        // Mise à jour du cellar courant.

        if (isOwner()){
            cellarsFromUser = Facade.getInstance().getCellarsFromUser(State.getInstance().getCurrentUser().getId());
        }
        scrollableBorderPane.getChildren().clear();

        createDetailPage();

    }

    /**
     * Tells if the current user is the owner of the cellar.
     *
     * @return true if the current user is the owner of the cellar, a manager or an admin, false otherwise.
     */
    public boolean isOwner(){
        // Si aucun utilisateur n'est connecté, on considère que l'utilisateur n'est pas le propriétaire.

        boolean isOwner;
        boolean isManager;

        // TODO check if user is admin
        if (State.getInstance().getCurrentUser() == null) {
            isOwner = false;
            isManager = false;
        } else {
            isOwner = State.getInstance().getCurrentUser().getId().equals(currentCellar.getOwnerRef());
            isManager = currentCellar.getManagers().contains(State.getInstance().getCurrentUser().getId());
//            boolean isAdmin = State.getInstance().getCurrentUser();
        }

        return isOwner || isManager;
    }

    // TODO CLEAN CODE
    // TODO HANDLE TRI TABLEAU
    public void createDetailPage(){
        // TODO clear previous content
        scrollableBorderPane.setCenter(null);

        mainVBox.getChildren().clear();

        // On stocke dans un booléen si le cellar est vu par son propriétaire ou non

        boolean isOwner = isOwner();

        // Récupérer le label et changer le texte

        // TODO CENTRER
        if (currentCellar != null) {
            cellarName.setText("Page de la cave: " +  currentCellar.getName());
        }

        // Setup le top de la page

        HBox hbox = new HBox();

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40.0);

        if(isOwner){
            // TODO faire qu'il marche
            String[] options = new String[cellarsFromUser.size()+1];
            int i = 0;
            while (i < cellarsFromUser.size()){
                options[i] = cellarsFromUser.get(i).getName();
                i++;
            }
            options[i] = "Voir toutes ses bouteilles";
            Select select = new Select("Test", true, options);
            if (seeAllBottles){
                select.getChoiceBox().setValue("Voir toutes ses bouteilles");
            } else {
                select.getChoiceBox().setValue(currentCellar.getName());
            }
            select.getChoiceBox().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Test: " + newValue);
                if (newValue.intValue() < cellarsFromUser.size()){
                    seeAllBottles = false;
                    State.getInstance().setSelectedCellar(cellarsFromUser.get(newValue.intValue()));

                } else {
                    System.out.println("Voir toutes les bouteilles");
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

        Button createCellarButton = new Button("Créer");
        createCellarButton.setCursor(Cursor.HAND);
        createCellarButton.setOnAction(event -> {
            // TODO CREATE NEW CELLAR with the name input in the previous field
        });

        hbox.getChildren().add(createCellarButton);

        scrollableBorderPane.setTop(hbox);

        // barre de recherche

        HBox searchHbox = new HBox();

        LabelField searchField = new LabelField("Rechercher une bouteille", false);

        Button researchButton = new Button("Rechercher");

        researchButton.setCursor(Cursor.HAND);
        // TODO researchButton.setOnAction(event -> {

        searchHbox.getChildren().addAll(searchField, researchButton);

        mainVBox.getChildren().add(searchHbox);

        // boutton de mise à jour (dispo seulement si on est le propriétaire)

        if(isOwner){
            Button updateButton = new Button("Modifer la cave");
            updateButton.setCursor(Cursor.HAND);
            updateButton.setOnAction(event -> {
                sceneHelper.bringNodeToFront(UpdateCellarForm.class.getSimpleName());
            });

            mainVBox.getChildren().add(updateButton);

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
            mainVBox.getChildren().add(deleteButton);
        }

        // create table

        if (seeAllBottles && isOwner) {
            System.out.println("bouteilles");
            List<BottleQuantity> bottleListFromUser = new ArrayList<>();
            // TODO EXTRACT BOTTLEQUANTITY FROM CELLAR
            for (Cellar cellar : cellarsFromUser) {
                cellar.getWalls().forEach(wall -> {
                    wall.getEmplacementBottleMap().forEach((emplacement) -> {
                        bottleListFromUser.addAll(emplacement.getBottleList());
                    });
                });
//                bottleListFromUser.addAll(Facade.getInstance().getBottlesFromCellar(cellar.getId()));
            }

            ArrayList<String> tableHeaders = new ArrayList<>();
            tableHeaders.add("Nom");
            tableHeaders.add("Catégorie");
            tableHeaders.add("Producteur");
            tableHeaders.add("Quantité");
//            tableHeaders.add("Cave");
            // TODO ADD CAVE AND ACTIONS

            TableView<List<String>> table = createTable(tableHeaders);

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


    // TODO CLEAN CODE
    public void createAndAddCurrentTable(){

        boolean isOwner = isOwner();

        // Creating table header

        ArrayList<String> tableHeaders = new ArrayList<>();
        tableHeaders.add("Nom");
        tableHeaders.add("Catégorie");
        tableHeaders.add("Producteur");
        tableHeaders.add("Quantité");

        if (isOwner){
            Button addWallButton = new Button("Ajouter un mur");
            addWallButton.setCursor(Cursor.HAND);
            addWallButton.setOnAction(event -> {
                sceneHelper.bringNodeToFront(AddWallForm.class.getSimpleName());
            });
            mainVBox.getChildren().add(addWallButton);
        }

        for (Wall wall: currentCellar.getWalls()) {

            mainVBox.getChildren().add(new Label("Mur: " + wall.getName()));

            if (isOwner) {
                Button addEmplacementButton = new Button("Ajouter un emplacement");
                addEmplacementButton.setCursor(Cursor.HAND);
                addEmplacementButton.setOnAction(event -> {
                    try {
                        Facade.getInstance().addEmplacement(State.getInstance().getSelectedCellar(), wall, new EmplacementBottle(new ArrayList<>(), new ArrayList<>()));
                    } catch (BadArgumentsException e) {
                        throw new RuntimeException(e);
                        // TODO handle exception
                    }
                    refresh();
                });
                mainVBox.getChildren().add(addEmplacementButton);
            }

            for (EmplacementBottle emplacementBottle: wall.getEmplacementBottleMap()) {

                mainVBox.getChildren().add(new Label("Emplacement n°" + (wall.getEmplacementBottleMap().indexOf(emplacementBottle)+1)));

                HBox tableActionsHbox = new HBox();

                tableActionsHbox.setPrefWidth(1260);

                TableView<List<String>> table = createTable(tableHeaders);
                table.setPrefWidth(1200);

                VBox actionsVbox = new VBox();
                actionsVbox.setPrefWidth(60);

                actionsVbox.getChildren().add(new Label("Actions"));

                tableActionsHbox.getChildren().addAll(table, actionsVbox);

                if (isOwner){
                    Button addBottleButton = new Button("Ajouter une bouteille");
                    addBottleButton.setCursor(Cursor.HAND);
                    addBottleButton.setOnAction(event -> {
                        State.getInstance().setSelectedEmplacementBottle(emplacementBottle);
                        State.getInstance().setSelectedWall(wall);
                        sceneHelper.bringNodeToFront(CreateBottleForm.class.getSimpleName());
                    });
                    mainVBox.getChildren().add(addBottleButton);
                }

                for (BottleQuantity bottleQuantity: emplacementBottle.getBottleList()) {

                    ArrayList<String> bottle = new ArrayList<>();
                    bottle.add(bottleQuantity.getBottle().getBottleName());
                    bottle.add(bottleQuantity.getBottle().getCategory());
                    bottle.add(bottleQuantity.getBottle().getProducer());
                    bottle.add(String.valueOf(bottleQuantity.getQuantity()));

                    table.getItems().add(bottle);

                    HBox actionsHbox = new HBox();

                    // TODO check to put icon in button
                    Button detailButton = new Button("D");
                    detailButton.setCursor(Cursor.HAND);
                    detailButton.setOnAction(event -> {
                        State.getInstance().setSelectedCellar(currentCellar);
                        State.getInstance().setSelectedWall(wall);
                        State.getInstance().setSelectedEmplacementBottle(emplacementBottle);
                        State.getInstance().setSelectedBottle(bottleQuantity.getBottle());
                        sceneHelper.bringNodeToFront(BottleDetails.class.getSimpleName());
                    });
                    actionsHbox.getChildren().add(detailButton);

                    // TODO handle delete in front
                    if(isOwner){

                        Button deleteButton2 = new Button("Supprimer");
                        deleteButton2.setCursor(Cursor.HAND);

                        Button addButton = new Button("+");
                        addButton.setCursor(Cursor.HAND);
                        addButton.setOnAction(event -> {
                            try {
                                Facade.getInstance().increaseBottleQuantity(currentCellar,wall,emplacementBottle,bottleQuantity);
                            } catch (BadArgumentsException e) {
                                //TODO afficher une erreur
                                throw new RuntimeException(e);
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
                                //TODO afficher une erreur
                                throw new RuntimeException(e);
                            }
                        });

                        actionsHbox.getChildren().addAll(addButton, removeButton);
                    }

                    actionsVbox.getChildren().add(actionsHbox);

//                    vbox.getChildren().add(new Label("Bouteille: " + bottleQuantity.getBottle().getBottleName() + " - Quantité: " + bottleQuantity.getQuantity()));
                }
                mainVBox.getChildren().add(tableActionsHbox);
            }


            // TODO Add avertisement entre chaque mur



        }
        // TODO add table to the center of the page
    }

    private TableView<List<String>> createTable(ArrayList<String> headers){

        // TODO REFACTOR NAME
        TableView<List<String>> table = new TableView<>();

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (String header: headers) {
            TableColumn<List<String>, String> column = new TableColumn<>(header);
            column.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(headers.indexOf(header))));
            table.getColumns().add(column);
        }

        return table;

    }
}
