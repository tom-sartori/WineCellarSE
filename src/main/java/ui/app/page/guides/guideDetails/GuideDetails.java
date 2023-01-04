package ui.app.page.guides.guideDetails;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import persistence.entity.guide.Guide;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;

import java.lang.reflect.Array;
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
    private Label titleGuide;

    @FXML
    private Label titleParagraph;

    @FXML
    private Pane paneLabel;


    private final State state = State.getInstance();

    private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

    //private Guide guide;

    private List<String> listContenu1 = new ArrayList<>();
    private List<String> listTitre = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Guide guide = State.getInstance().getCurrentGuide();
        if (guide != null ){
            //this.guide = guide;

            titleGuide.setText(guide.getTitle());

/*
            // Récupérer les valeurs et les clés
            for (Map.Entry<String, List<String>> entry : guide.getSectionList().entrySet()) {
                listTitre.add(entry.getKey()); // liste de tout les titres de paragraphe du guide
                List<String> value = entry.getValue();
                for (String i : value){
                    listContenu1.add(i); //liste de tous les paragraphes du guide
                }
            }
            */
            for(Map.Entry<String, String> entry : guide.getSectionList().entrySet()){
                int x = 0;
                Label label = new Label(entry.getKey());
                label.setLayoutY(x+250.0);
                x+=50;
                paneLabel.getChildren().add(label);


            }


        }

    }



    public void onAction() {
        this.initialize(null, null);
    }

}
