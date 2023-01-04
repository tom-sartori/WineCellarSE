package ui.app.page.guides.guideParagraphe;

import javafx.fxml.Initializable;
import persistence.entity.guide.Guide;
import ui.app.State;

import java.net.URL;
import java.util.ResourceBundle;

public class GuideParagraphe implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Guide guide = State.getInstance().getCurrentGuide();


    }

    public void onAction() {
        this.initialize(null, null);
    }
}
