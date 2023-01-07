package ui.app.page.cellar.forms.update;

import exception.BadArgumentsException;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
        // TODO remove on master
        Button refresh = new Button("Refresh");
        refresh.onActionProperty().set(event -> {
            refresh();
        });
        updateCellarFormSection.setTop(refresh);

        // TODO replace with state cellar on master.
        if (currentCellar != null) {
            refresh();
        }
    }

    /**
     * Refresh the page.
     */
    public void refresh(){
        updateCellarFormSection.setCenter(null);
        updateManagersReaders.getChildren().clear();

        currentCellar = State.getInstance().getSelectedCellar();

        if (isOwner() && currentCellar != null) {
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

        // TODO REMOVE ON MASTER.
        Button refresh = new Button("Refresh");
        refresh.onActionProperty().set(event -> {
            refresh();
        });
        form.getChildren().add(refresh);

        form.getChildren().add(new Label("Nom de la cave"));

        TextField name = new TextField(currentCellar.getName());
        form.getChildren().add(name);

        // TODO mettre une image sur une cave ?!
//        form.getChildren().add(new Label("Image de la cave ?"));
//        TextField image = new TextField(currentCellar.);

        form.getChildren().add(new Label("Cave publique ?"));
        CheckBox isPublic = new CheckBox();
        isPublic.setSelected(currentCellar.isPublic());
        form.getChildren().add(isPublic);

        HBox buttons = new HBox();

        Button validateButton = createButton("Valider");
        validateButton.onActionProperty().set(event -> {
            validateAction(name.getText(), isPublic.isSelected());
        });
        buttons.getChildren().add(validateButton);

        Button deleteButton = createButton("Supprimer");
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
        VBox managersReaders = new VBox();
        managersReaders.setSpacing(10);
        managersReaders.setPadding(new Insets(10, 10, 10, 10));

        HBox titleBox = createTitleBox("Gestion des managers et lecteurs", 20);
        titleBox.setPrefWidth(280.0);

        managersReaders.getChildren().add(titleBox);

        VBox managers = createBoxForManagersOrReaders();
        HBox managersBoxTitle = createTitleBox("Gestionnaires", 18);

        Button addManager = createButton("+");
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

        Button addReader = createButton("+");
        addReader.onActionProperty().set(event -> {
            addReaderOrManagerAction("reader");
        });
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

        Button deleteManager = createButton("-");
        deleteManager.onActionProperty().set(event -> {
            deleteManagerOrReaderAction(oneUser, managerOrReader);
        });

        hBox.getChildren().add(deleteManager);

        return hBox;
    }

    /**
     * Create a button with the text.
     *
     * @param text the text of the button.
     *
     * @return the button with the text .
     */
    public Button createButton(String text){
        Button button = new Button(text);
        button.setCursor(javafx.scene.Cursor.HAND);
        return button;
    }

    /**
     * Adds a manager to the cellar.
     */
    public void addReaderOrManagerAction(String managerOrReader){
        List<User> userList = Facade.getInstance().getUserList();
        ArrayList<String> userNames = new ArrayList<>();
        for (User user : userList) {
            userNames.add(user.getUsername());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", userNames);
        Optional<String> o = dialog.showAndWait();

        // TODO get the user from username with new method in facade.
        // TODO add the user as manager or reader of the cellar.

        refresh();
    }

    public void deleteManagerOrReaderAction(User user, String managerOrReader){
        Alert alert = createAlert("Suppression d'un " + managerOrReader, "Suppression d'un " + managerOrReader,"Êtes-vous sûr de vouloir supprimer le " + managerOrReader +  "?", Alert.AlertType.CONFIRMATION);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                if (managerOrReader.equals("manager")){
                    Facade.getInstance().removeCellarManager(currentCellar.getId(), user.getId());
                } else {
                    Facade.getInstance().removeCellarReader(currentCellar.getId(), user.getId());
                }
                refresh();
            } catch (BadArgumentsException e) {
                Alert error = createAlert("Erreur", "Erreur", e.getMessage(), Alert.AlertType.ERROR);
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
        currentCellar.setName(name);
        currentCellar.setPublic(isPublic);
        Facade.getInstance().updateOneCellar(currentCellar.getId(), currentCellar);
        refresh();
    }

    /**
     * delete the current cellar after confirmation of the user.
     */
    public void deleteCellarAction(){
        Alert alert = createAlert("Suppression d'une cave", "Suppression d'une cave","Êtes-vous sûr de vouloir supprimer la cave?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Facade.getInstance().deleteOneCellar(currentCellar.getId());
            State.getInstance().setSelectedCellar(null);

            sceneHelper.bringNodeToFront(PublicCellars.class.getSimpleName());
        }
    }

    /**
     * Create an alert.
     *
     * @param title the title of the alert
     * @param header the header of the alert
     * @param content the content of the alert
     * @param type the type of the alert
     *
     * @return the alert created.
     */
    public Alert createAlert(String title, String header, String content, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    // TODO remove this method on master.
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
}
