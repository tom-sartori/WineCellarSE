package constant;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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
}
