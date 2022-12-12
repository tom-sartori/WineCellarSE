package ui.app.theme.topmenupane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TopMenuPane implements Initializable {
    
    @FXML
    private Label pageNameLabel;
    
    @FXML
    public void changePageNameLabel(String pageName) {
        pageNameLabel.setText(pageName);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
