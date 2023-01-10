package ui.app.page.user.logout;

import facade.Facade;
import javafx.fxml.Initializable;
import ui.app.helpers.CustomSceneHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class Logout implements Initializable {

    private static boolean isAppReady = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (isAppReady) {
            Facade.getInstance().logout();
            CustomSceneHelper customSceneHelper = new CustomSceneHelper();
            customSceneHelper.refreshMenu();
            customSceneHelper.bringNodeToFront("login");
        }
        isAppReady = true; // This is to prevent the logout to be called when the app is not ready.
    }
}
