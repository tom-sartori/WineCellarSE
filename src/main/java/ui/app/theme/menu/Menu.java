package ui.app.theme.menu;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import ui.app.theme.menu.item.MenuItem;
import ui.app.theme.menu.role.MenuEnumAdmin;
import ui.app.theme.menu.role.MenuEnumPublic;
import ui.app.theme.menu.role.MenuEnumUser;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    private GridPane itemHolder;

    private ObservableList<MenuItem> menuItemList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemHolder.getChildren().clear();

        // Clear holder.
        menuItemList = FXCollections.observableArrayList();
        menuItemList.clear();

        if (Facade.getInstance().isAdminLogged()) {
            // Show admin menu.
            Arrays.stream(MenuEnumAdmin.values())
                    .filter(MenuEnumAdmin::isParentMenu)
                    .forEach(menuParent -> menuItemList.add(new MenuItem(menuParent, menuParent.getSubMenus())) );
        }
        else if (Facade.getInstance().isUserLogged()) {
            // Show user menu.
            Arrays.stream(MenuEnumUser.values())
                    .filter(MenuEnumUser::isParentMenu)
                    .forEach(menuParent -> menuItemList.add(new MenuItem(menuParent, menuParent.getSubMenus())) );
        }
        else {
            // Show public menu.
            Arrays.stream(MenuEnumPublic.values())
                    .filter(MenuEnumPublic::isParentMenu)
                    .forEach(menuParent -> menuItemList.add(new MenuItem(menuParent, menuParent.getSubMenus())) );
        }

        int count = 0;
        for (MenuItem menuItem : menuItemList) {
            itemHolder.add(menuItem,  count, 1);
            count++;
        }
    }
}
