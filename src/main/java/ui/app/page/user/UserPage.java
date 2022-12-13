package ui.app.page.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import ui.app.page.user.login.Login;
import ui.app.page.user.register.Register;

import java.net.URL;
import java.util.ResourceBundle;

public class UserPage implements Initializable {

    @FXML
    private Login loginController;

    @FXML
    private Register registerController;

    @FXML
    private ChoiceBox<String> choiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLoginVisible();

        choiceBox.getItems().add("Se connecter");
        choiceBox.getItems().add("S'inscrire");
    }

    public void setRegisterVisible() {
        loginController.setVisible(false);
        registerController.setVisible(true);
    }

    public void setLoginVisible() {
        registerController.setVisible(false);
        loginController.setVisible(true);
    }

    public void onAction() {
        if (choiceBox.getValue().equals("Se connecter")) {
            setLoginVisible();
        }
        else {
            setRegisterVisible();
        }
    }
}
