package ui.app.page.guides.guideDetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import persistence.entity.guide.Guide;
import ui.app.State;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.guides.list.GuideList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GuideDetails extends Pane implements Initializable {
    public AnchorPane guideDetails;
    @FXML
    private AnchorPane guideDetailsController;

    @FXML
    private VBox paneLabel;

    @FXML
    private AnchorPane anchorButtonRetour;


    private final State state = State.getInstance();

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    //private Guide guide;

    private List<String> listContenu1 = new ArrayList<>();
    private List<String> listTitre = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Guide guide = State.getInstance().getCurrentGuide();
        if (guide != null ){

            paneLabel.setAlignment(Pos.CENTER);
            paneLabel.setPadding(new Insets(30, 10, 10, 10));
            paneLabel.setPrefWidth(900);

            Label titreGuide = new Label();
            titreGuide.setLayoutY(177.0);
            titreGuide.setPrefHeight(36.0);
            titreGuide.setPrefWidth(1183.0);
            titreGuide.setFont(Font.font("System Bold", 25.0));
            titreGuide.setText(guide.getTitle());
            paneLabel.getChildren().add(titreGuide);

            for (Map.Entry<String, String> entry : guide.getSectionList().entrySet()) {
                String titre = entry.getKey();
                String contenu = entry.getValue();

                // Créer un TextFlow pour chaque titre et contenu
                TextFlow titreTextFlow = new TextFlow();
                Text titreText = new Text(titre);
                titreText.setFont(Font.font("System", FontWeight.BOLD, 25));
                titreTextFlow.getChildren().add(titreText);
                //titreTextFlow.setTextAlignment(TextAlignment.CENTER);

                TextFlow contenuTextFlow = new TextFlow();
                Text contenuText = new Text(contenu);
                contenuTextFlow.getChildren().add(contenuText);
                //contenuTextFlow.setTextAlignment(TextAlignment.CENTER);

                titreTextFlow.setPadding(new Insets(10, 0, 0, 0));
                contenuTextFlow.setPadding(new Insets(10, 0, 0, 0));


                // Ajouter les TextFlow au Pane

                paneLabel.getChildren().add(titreTextFlow);
                paneLabel.getChildren().add(contenuTextFlow);
            }
        }
        Button buttonRetour = new Button("Retour");
        buttonRetour.setOnAction(e -> {
            paneLabel.getChildren().clear(); // retire tous les enfants de la vue avant de les remplacer
            sceneHelper.bringNodeToFront(GuideList.class.getSimpleName());
        });

        anchorButtonRetour.getChildren().add(buttonRetour);

    }

    public void onAction() {
        this.initialize(null, null);
    }



}
