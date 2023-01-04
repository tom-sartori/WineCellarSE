package ui.app.page.cellar.updatecellar;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;
import ui.app.State;

import java.net.URL;
import java.util.ResourceBundle;

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
        currentCellar = State.getInstance().getSelectedCellar();

        System.out.println("currentCellar = " + currentCellar);
        // TODO and isOwner
        setupForm();

        setupManagersReaders();


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

        buttons.getChildren().add(new Button("Valider"));
        buttons.getChildren().add(new Button("Supprimer la cave"));

        form.getChildren().add(buttons);

        updateCellarFormSection.setCenter(form);
    }

    public void setupManagersReaders(){
        updateManagersReaders.getChildren().add(new Label("Gestionnaires et lecteurs"));

        if (State.getInstance().getSelectedCellar() != null) {
            Cellar currentCellar = State.getInstance().getSelectedCellar();

            VBox managersReaders = new VBox();

            managersReaders.getChildren().add(new Label("Gestionnaires"));

            User masterManager = Facade.getInstance().getOneUser(currentCellar.getOwnerRef());

            managersReaders.getChildren().add(new Label(masterManager.getUsername()));

            for (int i = 0; i < currentCellar.getManagers().size(); i++) {
                User oneUser = Facade.getInstance().getOneUser(currentCellar.getManagers().get(i));
                managersReaders.getChildren().add(new Label(oneUser.getUsername()));
            }

            managersReaders.getChildren().add(new Label("Lecteurs"));

            for (int i = 0; i < currentCellar.getReaders().size(); i++) {
                User oneUser = Facade.getInstance().getOneUser(currentCellar.getReaders().get(i));
                managersReaders.getChildren().add(new Label(oneUser.getUsername()));
            }

            updateManagersReaders.getChildren().add(managersReaders);
        }

        updateManagersReaders.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
}
