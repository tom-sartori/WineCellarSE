package ui.app.page.cellar.details;

import exception.BadArgumentsException;
import facade.Facade;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import ui.app.State;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.field.select.Select;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.updatecellar.UpdateCellarForm;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// TODO SINGLETON TO BE ABLE TO USE REFRESH METHOD IN OTHER CLASSES ?
public class CellarDetails implements Initializable {

    @FXML
    private BorderPane scrollableBorderPane;

    @FXML
    private Label cellarName;

    private Cellar currentCellar;

    // TODO Singleton ?!
    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
    }

    public void refresh(){
        if (State.getInstance().getSelectedCellar() != null) {
            System.out.println("CellarDetails: " + State.getInstance().getSelectedCellar().getName());
            currentCellar = State.getInstance().getSelectedCellar();
            createDetailPage();
        }
    }

    /**
     * Tells if the current user is the owner of the cellar.
     *
     * @return true if the current user is the owner of the cellar, a manager or an admin, false otherwise.
     */
    public boolean isOwner(){
        // Si aucun utilisateur n'est connecté, on considère que l'utilisateur n'est pas le propriétaire.

        boolean isOwner;

        // TODO also check if user is in list of cellar managers or is admin
        if (State.getInstance().getCurrentUser() == null) {
            isOwner = false;
        } else {
            isOwner = State.getInstance().getCurrentUser().getId().equals(currentCellar.getOwnerRef());
        }

        return isOwner;
    }

    // TODO CLEAN CODE
    // TODO HANDLE TRI TABLEAU
    public void createDetailPage(){
        // TODO clear previous content
        scrollableBorderPane.setCenter(null);

        // On stocke dans un booléen si le cellar est vu par son propriétaire ou non

        boolean isOwner = isOwner();

        // Récupérer le label et changer le texte

        // TODO CENTRER
        cellarName.setText("Page de la cave:  " + currentCellar.getName());

        // Setup le top de la page
        HBox hbox = new HBox();

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40.0);

        if(isOwner){
            // TODO faire qu'il marche
            hbox.getChildren().add(new Select("Test", true, new String[]{"Test1", "Test2", "Test3"}));
        }
        else{
            Label label = new Label(currentCellar.getName());
            label.setFont(javafx.scene.text.Font.font("System Bold", 30));
            hbox.getChildren().add(label);
        }

        LabelField labelField = new LabelField("Créer une nouvelle cave", true);

        hbox.getChildren().add(labelField);

        Button createCellarButton = new Button("Créer");
        createCellarButton.setCursor(Cursor.HAND);
        createCellarButton.setOnAction(event -> {
            // TODO CREATE NEW CELLAR with the name inpute in the previous field
        });

        hbox.getChildren().add(createCellarButton);

        scrollableBorderPane.setTop(hbox);

        // faire le centre de la page

        VBox vbox = new VBox();

        // barre de recherche

        HBox searchHbox = new HBox();

        LabelField searchField = new LabelField("Rechercher une bouteille", false);

        Button researchButton = new Button("Rechercher");

        researchButton.setCursor(Cursor.HAND);
        // TODO researchButton.setOnAction(event -> {

        searchHbox.getChildren().addAll(searchField, researchButton);

        vbox.getChildren().add(searchHbox);

        // boutton de mise à jour (dispo seulement si on est le propriétaire)

        if(isOwner){
            Button updateButton = new Button("Mettre à jour la cave");
            updateButton.setCursor(Cursor.HAND);
            updateButton.setOnAction(event -> {
                sceneHelper.bringNodeToFront(UpdateCellarForm.class.getSimpleName());
            });
            vbox.getChildren().add(updateButton);
        }

        // create table

        ArrayList<String> tableHeaders = new ArrayList<>();
        tableHeaders.add("Nom");
        tableHeaders.add("Catégorie");
        tableHeaders.add("Producteur");
        tableHeaders.add("Quantité");

        for (Wall wall: currentCellar.getWalls()) {
            HBox tableActionsHbox = new HBox();

            tableActionsHbox.setPrefWidth(1260);

            TableView<List<String>> table = createTable(tableHeaders);
            table.setPrefWidth(1200);

            VBox actionsVbox = new VBox();
            actionsVbox.setPrefWidth(60);

            actionsVbox.getChildren().add(new Label("Actions"));

            tableActionsHbox.getChildren().addAll(table, actionsVbox);

            vbox.getChildren().add(new Label("Mur: " + wall.getName()));

            for (EmplacementBottle emplacementBottle: wall.getEmplacementBottleMap()) {
                vbox.getChildren().add(new Label("Emplacement n°" + wall.getEmplacementBottleMap().indexOf(emplacementBottle) + 1));

                if (isOwner){
                    Button addBottleButton = new Button("Ajouter une bouteille");
                    addBottleButton.setCursor(Cursor.HAND);
                    addBottleButton.setOnAction(event -> {
                        try {
                            Facade.getInstance().insertBottle(wall, currentCellar, new Bottle(), emplacementBottle);
                        } catch (BadArgumentsException e) {
                            // TODO handle error
                            throw new RuntimeException(e);
                        }
                    });
                    vbox.getChildren().add(addBottleButton);
                }

                for (BottleQuantity bottleQuantity: emplacementBottle.getBottleList()) {
                    ArrayList<String> bottle = new ArrayList<>();
                    bottle.add(bottleQuantity.getBottle().getBottleName());
                    bottle.add(bottleQuantity.getBottle().getCategory());
                    bottle.add(bottleQuantity.getBottle().getProducer());
                    bottle.add(String.valueOf(bottleQuantity.getQuantity()));

                    table.getItems().add(bottle);

                    // TODO handle delete in front
                    if(isOwner){

                        Button deleteButton2 = new Button("Supprimer");
                        deleteButton2.setCursor(Cursor.HAND);



                        HBox actionsHbox = new HBox();

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
                            } catch (BadArgumentsException e) {
                                //TODO afficher une erreur
                                throw new RuntimeException(e);
                            }
                            //TODO FIX
                            if (bottleQuantity.getQuantity() == 0) {
                                System.out.println("on passe ici");
                                table.getItems().remove(bottle);
                                table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));

                            }
                            else{
                                refresh();
                            }
                        });

                        actionsHbox.getChildren().addAll(addButton, removeButton);

                        actionsVbox.getChildren().add(actionsHbox);
                    }

//                    vbox.getChildren().add(new Label("Bouteille: " + bottleQuantity.getBottle().getBottleName() + " - Quantité: " + bottleQuantity.getQuantity()));
                }
            }

            if (isOwner){
                vbox.getChildren().add(tableActionsHbox);
            }
            else{
                table.setPrefWidth(1260);
                vbox.getChildren().add(table);
            }
        }

        scrollableBorderPane.setCenter(vbox);

        // TODO Add avertisement entre chaque mur
    }

    private TableView<List<String>> createTable(ArrayList<String> headers){

        // TODO REFACTOR NAME
        TableView<List<String>> unitsTableView = new TableView<>();

        unitsTableView.setFixedCellSize(25);
        unitsTableView.prefHeightProperty().bind(Bindings.size(unitsTableView.getItems()).multiply(unitsTableView.getFixedCellSize()).add(30));

        unitsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (String header: headers) {
            TableColumn<List<String>, String> column = new TableColumn<>(header);
            column.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(headers.indexOf(header))));
            unitsTableView.getColumns().add(column);
        }

        return unitsTableView;

    }
}
