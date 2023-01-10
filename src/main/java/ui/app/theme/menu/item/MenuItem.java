package ui.app.theme.menu.item;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ui.app.helpers.CustomSceneHelper;
import ui.app.theme.menu.role.MenuEnumInterface;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MenuItem extends Pane {

    @FXML
    private AnchorPane menuItem;

    @FXML
    private SplitMenuButton menuButton;

    private final CustomSceneHelper sceneHelper;


    public MenuItem(MenuEnumInterface menuEnum, List<? extends MenuEnumInterface> subMenuList) {
        // The component has been imported from the constructor ok a .java class.
        super();
        try {
            // Get the fxml file associated.
            menuItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MenuItem.fxml")));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialize the scene helper.
        this.sceneHelper = new CustomSceneHelper();

        // Get fxml items from the fxml file.
        getChildren().add(menuItem);
        menuButton = (SplitMenuButton) menuItem.lookup("#menuButton");
        menuButton.setText(menuEnum.getNavigationTitle());

        // Add sub menu items.
        subMenuList.forEach(subMenu -> {
                    javafx.scene.control.MenuItem fxMenuItem = new javafx.scene.control.MenuItem(subMenu.getNavigationTitle());
                    setOnAction(fxMenuItem, subMenu.getControllerClass());
                    menuButton.getItems().add(fxMenuItem);
                });

        setOnAction(menuButton, menuEnum.getControllerClass());
    }

    private void setOnAction(SplitMenuButton menuButton, Class<?> page) {
        if (page != null) {
            menuButton.setOnAction(actionEvent -> this.sceneHelper.bringNodeToFront(page.getSimpleName()));
        }
    }

    private void setOnAction(javafx.scene.control.MenuItem fxMenuItem, Class<?> page) {
        fxMenuItem.setOnAction(actionEvent -> this.sceneHelper.bringNodeToFront(page.getSimpleName()));
    }
}
