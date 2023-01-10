package ui.app.page.cellar.forms.update;

import constant.NodeCreations;
import exception.BadArgumentsException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.bson.types.ObjectId;
import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.lists.publiccellars.PublicCellars;

import java.net.URL;
import java.util.*;

public class UpdateCellarForm implements Initializable {

    @FXML
    private AnchorPane updateManagersReaders;

    @FXML
    private BorderPane updateCellarFormSection;

    private Cellar currentCellar;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (State.getInstance().getSelectedCellar() != null) {
            refresh();
        }
    }

    /**
     * Refresh the page.
     */
    public void refresh(){
        updateCellarFormSection.setCenter(null);
        updateManagersReaders.getChildren().clear();

        // updating current cellar
        State.getInstance().setSelectedCellar(Facade.getInstance().getOneCellar(State.getInstance().getSelectedCellar().getId()));

        currentCellar = State.getInstance().getSelectedCellar();

        if (Facade.getInstance().isManagerOfCellar(State.getInstance().getSelectedCellar().getId()) && currentCellar != null) {
            setupForm();
            setupManagersReaders();
        }
    }

    /**
     * Set up the form to update the cellar.
     */
    public void setupForm(){
        Label modifierUneCave = new Label("Modifier une cave");
        modifierUneCave.setPadding(new Insets(10, 10, 10, 10));
        modifierUneCave.setFont(Font.font(30));
        updateCellarFormSection.setTop(modifierUneCave);

        VBox form = new VBox();

        form.getChildren().add(new Label("Nom de la cave"));

        TextField name = new TextField(currentCellar.getName());
        form.getChildren().add(name);

        form.getChildren().add(new Label("Cave publique ?"));
        CheckBox isPublic = new CheckBox();
        isPublic.setSelected(currentCellar.isPublic());
        form.getChildren().add(isPublic);

        HBox buttons = new HBox();

        Button validateButton = NodeCreations.createButton("Valider");
        validateButton.onActionProperty().set(event -> {
            validateAction(name.getText(), isPublic.isSelected());
        });
        buttons.getChildren().add(validateButton);

        Button deleteButton = NodeCreations.createButton("Supprimer");
        deleteButton.onActionProperty().set(event -> {
            deleteCellarAction();
        });

        buttons.getChildren().add(deleteButton);

        form.getChildren().add(buttons);

        updateCellarFormSection.setCenter(form);
    }

    /**
     * Set up the display of managers and readers of the cellar in the right section of the screen.
     */
    public void setupManagersReaders(){
        updateManagersReaders.getChildren().clear();
        VBox managersReaders = new VBox();
        managersReaders.setSpacing(10);
        managersReaders.setPadding(new Insets(10, 10, 10, 10));

        HBox titleBox = createTitleBox("Gestion des managers et lecteurs", 20);
        titleBox.setPrefWidth(280.0);

        managersReaders.getChildren().add(titleBox);

        VBox managers = createBoxForManagersOrReaders();
        HBox managersBoxTitle = createTitleBox("Gestionnaires", 18);

        Button addManager = NodeCreations.createButton("+");
        addManager.onActionProperty().set(event -> {
            addReaderOrManagerAction("manager");
        });
        managersBoxTitle.getChildren().add(addManager);
        managers.getChildren().add(managersBoxTitle);

        User masterManager = Facade.getInstance().getOneUser(currentCellar.getOwnerRef());
        managers.getChildren().add(new Label(masterManager.getUsername()));

        for (int i = 0; i < currentCellar.getManagers().size(); i++) {
            HBox manager = createManagerOrReader(currentCellar.getManagers().get(i), "manager");
            managers.getChildren().add(manager);
        }
        managersReaders.getChildren().add(managers);

        VBox readers = createBoxForManagersOrReaders();

        HBox readersBoxTitle = createTitleBox("Lecteurs", 18);

        Button addReader = NodeCreations.createButton("+");
        addReader.onActionProperty().set(event -> {
            addReaderOrManagerAction("reader");
        });
        readersBoxTitle.getChildren().add(addReader);
        readers.getChildren().add(readersBoxTitle);

        for (int i = 0; i < currentCellar.getReaders().size(); i++) {
            HBox reader = createManagerOrReader(currentCellar.getReaders().get(i), "reader");
            readers.getChildren().add(reader);
        }
        managersReaders.getChildren().add(readers);

        updateManagersReaders.getChildren().add(managersReaders);
        updateManagersReaders.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public VBox createBoxForManagersOrReaders(){
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        return vBox;
    }

    /**
     * Create a title box for managers or readers.
     *
     * @param title the title of the label
     * @param fontSize the font size of the title
     *
     * @return the title box with the title and the font size set in a HBox.
     */
    public HBox createTitleBox(String title, int fontSize){
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(fontSize));

        hBox.getChildren().add(titleLabel);

        return hBox;
    }

    /**
     * Create a manager or reader.
     *
     * @param userId the id of the user.
     * @param managerOrReader the type of the user.
     *
     * @return A Hbox with the information of the user and a button to remove him.
     */
    public HBox createManagerOrReader(ObjectId userId, String managerOrReader){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        User oneUser = Facade.getInstance().getOneUser(userId);

        hBox.getChildren().add(new Label(oneUser.getUsername()));

        Button deleteManager = NodeCreations.createButton("-");
        deleteManager.onActionProperty().set(event -> {
            deleteManagerOrReaderAction(oneUser, managerOrReader);
        });

        hBox.getChildren().add(deleteManager);

        return hBox;
    }

    /**
     * Adds a manager to the cellar.
     */
    public void addReaderOrManagerAction(String managerOrReader){
        List<User> userList = Facade.getInstance().getUserList();
        ArrayList<String> userNames = new ArrayList<>();
        List<ObjectId> managerOrReaderList = new ArrayList<>();
        if (managerOrReader.equals("manager")){
            managerOrReaderList = currentCellar.getManagers();
        } else {
            managerOrReaderList = currentCellar.getReaders();
        }
        for (User user : userList) {
            if (!managerOrReaderList.contains(user.getId())){
                userNames.add(user.getUsername());
            }
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", userNames);
        Optional<String> o = dialog.showAndWait();

        if (o.isPresent()) {
            String username = o.get();

            User user = Facade.getInstance().getOneUserByUsername(username);

            try {
                if (managerOrReader.equals("manager")) {
                    Facade.getInstance().addCellarManager(user.getId(), State.getInstance().getSelectedCellar().getId());
                } else {
                    Facade.getInstance().addCellarReader(user.getId(), State.getInstance().getSelectedCellar().getId());
                }
                refresh();
            } catch (Exception e) {
                NodeCreations.createAlert("Erreur", "Erreur lors de l'ajout du manager ou du lecteur.", e.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    public void deleteManagerOrReaderAction(User user, String managerOrReader){
        Alert alert = NodeCreations.createAlert("Suppression d'un " + managerOrReader, "Suppression d'un " + managerOrReader,"Êtes-vous sûr de vouloir supprimer le " + managerOrReader +  "?", Alert.AlertType.CONFIRMATION);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                if (managerOrReader.equals("manager")){
                    Facade.getInstance().removeCellarManager(user.getId(), State.getInstance().getSelectedCellar().getId());
                } else {
                    Facade.getInstance().removeCellarReader(user.getId(), State.getInstance().getSelectedCellar().getId());
                }
                refresh();
            } catch (BadArgumentsException e) {
                Alert error = NodeCreations.createAlert("Erreur", "Erreur", e.getMessage(), Alert.AlertType.ERROR);
                error.showAndWait();
            }
        }
    }

    /**
     * Updates the cellar according to parameters.
     *
     * @param name the new name of the cellar.
     * @param isPublic the new isPublic status of the cellar.
     */
    public void validateAction(String name, boolean isPublic){
        if (!Objects.equals(name, "")){
            currentCellar.setName(name);
            currentCellar.setPublic(isPublic);
        }else{
            Alert alert = NodeCreations.createAlert("Mauvais argument", "Veuillez entrer un nom valide", "Veuillez entrer un nom valide pour votre cave", Alert.AlertType.ERROR);
            alert.show();
        }

        try{
            Facade.getInstance().updateOneCellar(currentCellar.getId(), currentCellar);
        }catch (Exception e){
            Alert mauvaisArguments = NodeCreations.createAlert("Mauvais arguments", "Les valeurs de vos champs sont incorrectes !", e.getMessage(), Alert.AlertType.ERROR);
            mauvaisArguments.showAndWait();
        }
        refresh();
    }

    /**
     * delete the current cellar after confirmation of the user.
     */
    public void deleteCellarAction(){
        Alert alert = NodeCreations.createAlert("Suppression d'une cave", "Suppression d'une cave","Êtes-vous sûr de vouloir supprimer la cave?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Facade.getInstance().deleteOneCellar(currentCellar.getId());
            State.getInstance().setSelectedCellar(null);

            sceneHelper.bringNodeToFront(PublicCellars.class.getSimpleName());
        }
    }

}
