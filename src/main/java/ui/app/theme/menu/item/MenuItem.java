package ui.app.theme.menu.item;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ui.app.helpers.services.CustomSceneHelper;

import java.io.IOException;
import java.util.Objects;

public class MenuItem extends Pane {

    @FXML
    private AnchorPane menuItem;

    @FXML
    private Button button;

    private final CustomSceneHelper sceneHelper;

    private final String pageName;

    public MenuItem(String text, String pageName) {
        // The component has been imported from the constructor ok a .java class.
        super();
        try {
            menuItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MenuItem.fxml")));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        getChildren().add(menuItem);
        button = (Button) menuItem.lookup("#button");

        button.setText(text);

        button.setOnAction(actionEvent -> onclick());

        this.sceneHelper = new CustomSceneHelper();
        this.pageName = pageName;
    }

    private void onclick() {
        sceneHelper.bringNodeToFront(pageName);
    }

    public String getText() {
        return button.getText();
    }
}
