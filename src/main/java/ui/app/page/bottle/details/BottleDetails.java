package ui.app.page.bottle.details;

import exception.BadArgumentsException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistence.entity.bottle.Bottle;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.details.CellarDetails;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BottleDetails implements Initializable {

    @FXML
    private VBox mainPaneBottleDetails;

    @FXML
    private AnchorPane titlePaneBottleDetails;

    @FXML
    private AnchorPane logoBottlePane;

    private boolean isEdit = false;

    private ArrayList<TextField> textFields;

    private final CustomSceneHelper customSceneHelper = new CustomSceneHelper();

    @FXML
    private AnchorPane rateList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Nom | categorie
         * producteur
         * cépages | appellation
         * degré d'alcool | millesime
         * taille bouteille | unité bouteille
         * prix
         */

        textFields = new ArrayList<>();


        refresh();
    }

    public Button createEditButton(){
        Button editButton = new Button("Edit");
        editButton.setCursor(javafx.scene.Cursor.HAND);
        editButton.setOnAction(e -> {
            isEdit = !isEdit;
            if (isEdit) {
                editButton.setText("Save");
            } else {
                editButton.setText("Edit");
                // TODO verifier que les champs sont remplis et que les valeurs sont correctes

                Bottle selectedBottle = new Bottle();
                selectedBottle.setBottleName(textFields.get(0).getText());
                selectedBottle.setCategory(textFields.get(1).getText());
                selectedBottle.setProducer(textFields.get(2).getText());

                ArrayList<String> grapes = textFields.get(3).getText().equals("") ? new ArrayList<>() : new ArrayList<>(Arrays.asList(textFields.get(3).getText().split(",")));
                selectedBottle.setGrapeList(grapes);

                selectedBottle.setAppellation(textFields.get(4).getText());
                selectedBottle.setAlcoholPercentage(Double.parseDouble(textFields.get(5).getText()));
                selectedBottle.setVintage(Integer.parseInt(textFields.get(6).getText()));
                selectedBottle.setBottleSize(Double.parseDouble(textFields.get(7).getText()));
                selectedBottle.setSizeUnit(textFields.get(8).getText());
                selectedBottle.setPrice(Double.parseDouble(textFields.get(9).getText()));

                try {
                    Facade.getInstance().updateBottle(State.getInstance().getSelectedWall(), State.getInstance().getSelectedCellar(),State.getInstance().getSelectedBottle(), State.getInstance().getSelectedEmplacementBottle(), selectedBottle);
                    State.getInstance().setSelectedBottle(selectedBottle);
                } catch (BadArgumentsException ex) {
                    throw new RuntimeException(ex);
                }

                textFields = new ArrayList<>();
            }
            refresh();
        });

        return editButton;
    }

    public VBox createLabelField(String label, String value) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(5);
        vBox.setPadding(new javafx.geometry.Insets(10));

        Label label1 = new Label(label);
        label1.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 20));
        vBox.getChildren().add(label1);

        if(isEdit){
            TextField textField = new TextField(value);
            textFields.add(textField);
            vBox.getChildren().add(textField);
        }
        else{
            vBox.getChildren().add(new Label(value));
        }
        return vBox;
    }

    // TODO GENERALIZE WITH FUNCTION ABOVE
    public VBox createLabelField(String label, ArrayList<String> value) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(5);
        vBox.setPadding(new javafx.geometry.Insets(10));

        Label label1 = new Label(label);
        label1.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 20));
        vBox.getChildren().add(label1);

        if(isEdit){
            TextField textField = new TextField(String.join(",", value));
            textFields.add(textField);
            vBox.getChildren().add(textField);
        }
        else{
            vBox.getChildren().add(new Label(String.join(",", value)));
        }
        return vBox;
    }

    public void refresh(){
        titlePaneBottleDetails.getChildren().clear();

        HBox hBoxTitle = new HBox();

        hBoxTitle.getChildren().add(new Label("Bottle Details"));

        Button button2 = new Button("Retour");
        button2.setOnAction(e -> {
            State.getInstance().setSelectedBottle(null);
            customSceneHelper.bringNodeToFront(CellarDetails.class.getSimpleName());

        });
        hBoxTitle.getChildren().add(button2);

        titlePaneBottleDetails.getChildren().add(hBoxTitle);

        mainPaneBottleDetails.getChildren().clear();
        Bottle bottle = State.getInstance().getSelectedBottle();

        if(bottle != null){
            /**
             * Nom | categorie
             * producteur
             * cépages | appellation
             * degré d'alcool | millesime
             * taille bouteille | unité bouteille
             * prix
             */

            mainPaneBottleDetails.setAlignment(Pos.CENTER);

            HBox firstRow = new HBox();
            firstRow.setAlignment(Pos.CENTER);
            HBox secondRow = new HBox();
            secondRow.setAlignment(Pos.CENTER);
            HBox thirdRow = new HBox();
            thirdRow.setAlignment(Pos.CENTER);
            HBox fourthRow = new HBox();
            fourthRow.setAlignment(Pos.CENTER);
            HBox fifthRow = new HBox();
            fifthRow.setAlignment(Pos.CENTER);
            HBox sixthRow = new HBox();
            sixthRow.setAlignment(Pos.CENTER);

            firstRow.getChildren().add(createLabelField("Nom", bottle.getBottleName()));
            firstRow.getChildren().add(createLabelField("Catégorie", bottle.getCategory()));

            secondRow.getChildren().add(createLabelField("Producteur", bottle.getProducer()));

            thirdRow.getChildren().add(createLabelField("Cépages", bottle.getGrapeList()));
            thirdRow.getChildren().add(createLabelField("Appellation", bottle.getAppellation()));

            fourthRow.getChildren().add(createLabelField("Degré d'alcool", String.valueOf(bottle.getAlcoholPercentage())));
            fourthRow.getChildren().add(createLabelField("Millesime", String.valueOf(bottle.getVintage())));

            fifthRow.getChildren().add(createLabelField("Taille bouteille", String.valueOf(bottle.getBottleSize())));
            fifthRow.getChildren().add(createLabelField("Unité bouteille", bottle.getSizeUnit()));

            sixthRow.getChildren().add(createLabelField("Prix", String.valueOf(bottle.getPrice())));

            mainPaneBottleDetails.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow);

            if (Facade.getInstance().isManagerOfCellar(State.getInstance().getSelectedCellar().getId())) {
                Button editButton = createEditButton();

                mainPaneBottleDetails.getChildren().add(editButton);
            }

            logoBottlePane.getChildren().add(new Label("Logo"));

            Node rateListNode = (AnchorPane) mainPaneBottleDetails.getParent().lookup("#rateList");
            HBox rateListRow = new HBox();
            rateListRow.getChildren().add((AnchorPane) rateList);
            rateListRow.setAlignment(Pos.CENTER);
            mainPaneBottleDetails.getChildren().add(rateListRow);

        }
    }
}
