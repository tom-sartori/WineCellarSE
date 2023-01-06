package ui.app.page.cellar.updatecellar;

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
import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.cellar.publiccellars.PublicCellars;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

//TODO clean code
// TODO ajouter éventuellement un bouton pour changer l'image de la cave.
public class UpdateCellarForm implements Initializable {

    @FXML
    private AnchorPane updateManagersReaders;

    @FXML
    private BorderPane updateCellarFormSection;

    private Cellar currentCellar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Button refresh = new Button("Refresh");
        refresh.onActionProperty().set(event -> {
            refresh();
        });
        updateCellarFormSection.setTop(refresh);
        if (currentCellar != null) {
            refresh();
        }
    }

    public void refresh(){
        updateCellarFormSection.setCenter(null);
        updateManagersReaders.getChildren().clear();

        currentCellar = State.getInstance().getSelectedCellar();

        System.out.println("currentCellar = " + currentCellar);

        if (isOwner() && currentCellar != null) {
            setupForm();
            setupManagersReaders();
        }
    }

    public void setupForm(){
        Label modifierUneCave = new Label("Modifier une cave");
        modifierUneCave.setPadding(new Insets(10, 10, 10, 10));
        modifierUneCave.setFont(Font.font(30));
        updateCellarFormSection.setTop(modifierUneCave);

        VBox form = new VBox();

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

        Button validateButton = new Button("Valider");
        validateButton.setCursor(javafx.scene.Cursor.HAND);
        validateButton.onActionProperty().set(event -> {
            currentCellar.setName(name.getText());
            currentCellar.setPublic(isPublic.isSelected());
            Facade.getInstance().updateOneCellar(currentCellar.getId(), currentCellar);
            refresh();
        });

        buttons.getChildren().add(validateButton);

        Button deleteButton = new Button("Supprimer la cave");
        deleteButton.setCursor(javafx.scene.Cursor.HAND);
        deleteButton.onActionProperty().set(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression de la cave");
            alert.setHeaderText("Suppression de la cave");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer la cave ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Facade.getInstance().deleteOneCellar(currentCellar.getId());
                State.getInstance().setSelectedCellar(null);
                CustomSceneHelper sceneHelper = new CustomSceneHelper();
                sceneHelper.bringNodeToFront(PublicCellars.class.getSimpleName());
            }
        });

        buttons.getChildren().add(deleteButton);

        form.getChildren().add(buttons);

        updateCellarFormSection.setCenter(form);
    }

    public void setupManagersReaders(){
        VBox managersReaders = new VBox();

        managersReaders.setSpacing(10);
        managersReaders.setPadding(new Insets(10, 10, 10, 10));

        VBox titleBox = new VBox();

        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPrefWidth(280.0);

        Label gestionnairesEtLecteurs = new Label("Gestionnaires et lecteurs");
        gestionnairesEtLecteurs.setFont(Font.font(24));

        titleBox.getChildren().add(gestionnairesEtLecteurs);
        managersReaders.getChildren().add(titleBox);

        if (State.getInstance().getSelectedCellar() != null) {
            Cellar currentCellar = State.getInstance().getSelectedCellar();

            VBox managers = new VBox();
            managers.setAlignment(Pos.CENTER);
            managers.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            managers.setPadding(new Insets(10, 10, 10, 10));
            managers.setSpacing(10);

            HBox managersBoxTitle = new HBox();
            managersBoxTitle.setSpacing(10);
            managersBoxTitle.setAlignment(Pos.CENTER);

            Label managerBoxTitleLabel = new Label("Gestionnaires");
            managerBoxTitleLabel.setFont(Font.font(20));

            managersBoxTitle.getChildren().add(managerBoxTitleLabel);

            Button addManager = new Button("+");
            addManager.setCursor(javafx.scene.Cursor.HAND);
            addManager.onActionProperty().set(event -> {
                List<User> userList = Facade.getInstance().getUserList();
                ArrayList<String> userNames = new ArrayList<>();
                for (User user : userList) {
                    userNames.add(user.getUsername());
                }
                ChoiceDialog<String> dialog = new ChoiceDialog<>("", userNames);
                Optional<String> o = dialog.showAndWait();

                // TODO get the user from username with new method in facade.
                // TODO add the user as manager of the cellar.
            });

            managersBoxTitle.getChildren().add(addManager);

            managers.getChildren().add(managersBoxTitle);


            User masterManager = Facade.getInstance().getOneUser(currentCellar.getOwnerRef());

            managers.getChildren().add(new Label(masterManager.getUsername()));

            for (int i = 0; i < currentCellar.getManagers().size(); i++) {
                HBox manager = new HBox();
                manager.setAlignment(Pos.CENTER);
                manager.setSpacing(10);

                User oneUser = Facade.getInstance().getOneUser(currentCellar.getManagers().get(i));

                manager.getChildren().add(new Label(oneUser.getUsername()));

                Button deleteManager = new Button("-");
                deleteManager.setCursor(javafx.scene.Cursor.HAND);
                deleteManager.onActionProperty().set(event -> {
                    try {
                        Facade.getInstance().removeCellarManager(currentCellar.getId(), oneUser.getId());
                        refresh();
                    } catch (BadArgumentsException e) {
                        throw new RuntimeException(e);
                        //TODO handle exception
                    }
                    refresh();
                });

                manager.getChildren().add(deleteManager);

                managers.getChildren().add(manager);
            }

            managersReaders.getChildren().add(managers);

            VBox readers = new VBox();

            readers.setAlignment(Pos.CENTER);
            readers.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            readers.setPadding(new Insets(10, 10, 10, 10));
            readers.setSpacing(10);


            Label readerBoxTitle = new Label("Lecteurs");
            readerBoxTitle.setFont(Font.font(20));

            readers.getChildren().add(readerBoxTitle);

            for (int i = 0; i < currentCellar.getReaders().size(); i++) {
                User oneUser = Facade.getInstance().getOneUser(currentCellar.getReaders().get(i));
                readers.getChildren().add(new Label(oneUser.getUsername()));
            }

            managersReaders.getChildren().add(readers);

        }

        updateManagersReaders.getChildren().add(managersReaders);

        updateManagersReaders.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

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
