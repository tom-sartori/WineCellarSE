package constant;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public final class NodeCreations {

    private NodeCreations() { }

    /**
     * Create an alert.
     *
     * @param title the title of the alert
     * @param header the header of the alert
     * @param content the content of the alert
     * @param type the type of the alert
     *
     * @return the alert created.
     */
    public static Alert createAlert(String title, String header, String content, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    /**
     * Create a button with the text.
     *
     * @param text the text of the button.
     *
     * @return the button with the text .
     */
    public static Button createButton(String text){
        Button button = new Button(text);
        button.setCursor(javafx.scene.Cursor.HAND);
        return button;
    }

    /**
     * Create a table view with the columns.
     *
     * @param headers the headers of the columns.
     *
     * @return the table view with the columns.
     */
    public static TableView<List<String>> createTable(ArrayList<String> headers){

        TableView<List<String>> table = new TableView<>();

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setSortPolicy(param -> false);

        for (String header: headers) {
            TableColumn<List<String>, String> column = new TableColumn<>(header);
            column.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(headers.indexOf(header))));
            table.getColumns().add(column);
        }

        return table;
    }
}
