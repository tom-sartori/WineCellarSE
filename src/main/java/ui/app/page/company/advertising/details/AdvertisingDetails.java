package ui.app.page.company.advertising.details;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import persistence.entity.advertising.Advertising;
import ui.app.State;
import ui.app.helpers.CustomSceneHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdvertisingDetails implements Initializable {
    private Advertising advertising;

    @FXML
    private AnchorPane advertisingDetails;
    @FXML
    private ImageView image;
    @FXML
    private Label nom;
    @FXML
    private TextArea description, urlCompany;
    @FXML
    private Button retour, statistiques, modifier;

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Advertising advertising = State.getInstance().getCurrentAdvertising();

        retour.setOnAction(event -> this.sceneHelper.bringNodeToFront(State.getInstance().getPreviousPage()));
        if(advertising != null) {
            this.advertising = advertising;

            try {
                image.setImage(new Image(new URL(advertising.getLink()).openStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            nom.setText(advertising.getName());
            description.setText(advertising.getDescription());
            urlCompany.setText(advertising.getUrl());

            statistiques.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Statistiques");
                alert.setHeaderText(null);
                alert.setContentText("La publicité a été visionnée " + advertising.getNbViews() + " fois.");
                alert.showAndWait();
            });
        }
    }
    public void onAction() {
        this.initialize(null, null);
    }
}
