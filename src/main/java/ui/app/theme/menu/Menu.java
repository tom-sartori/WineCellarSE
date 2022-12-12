package ui.app.theme.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import ui.app.theme.menu.item.MenuItem;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    private GridPane itemHolder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemHolder.getChildren().clear();

        ObservableList<MenuItem> menuItemList = FXCollections.observableArrayList();
        menuItemList.clear();

        Arrays.stream(MenuEnum.values())
                .forEach(menuEnum -> menuItemList.add(new MenuItem(menuEnum.getNavName(), menuEnum.getPageName())));

        int count = 0;
        for (MenuItem menuItem : menuItemList) {
            itemHolder.add(menuItem,  count, 1);
            count++;
        }
    }
}
