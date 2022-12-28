package ui.app.page.cellar.updatecellar;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
    }

    public void refresh(){
        setupForm();

        setupManagersReaders();
    }

    public void setupForm(){
        Label modifierUneCave = new Label("Modifier une cave");
        modifierUneCave.setPadding(new Insets(10, 10, 10, 10));
        modifierUneCave.setFont(Font.font(30));
        updateCellarFormSection.setTop(modifierUneCave);

        VBox form = new VBox();

        Button refresh = new Button("Rafraichir");
        refresh.onActionProperty().set(event -> {
            refresh();
        });
        form.getChildren().add(refresh);

        form.getChildren().add(new Label("Nom de la cave"));
        form.getChildren().add(new Label("Image de la cave ?"));
        form.getChildren().add(new Label("Ajout de mur"));
        form.getChildren().add(new Label("Cave publique ?"));

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
