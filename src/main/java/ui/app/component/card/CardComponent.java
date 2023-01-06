package ui.app.component.card;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CardComponent extends BorderPane {
    @FXML private Label label;

    @FXML private VBox centerBox;

    @FXML private FlowPane topFlowPane;

    @FXML private FlowPane bottomFlowPane;

    public CardComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            topFlowPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            centerBox = new VBox();
            centerBox.setSpacing(2);
            centerBox.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
            setCenter(centerBox);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return label.textProperty();
    }

    public void addNode(Node node){
        centerBox.getChildren().add(node);
    }

    public void addBottomNode(Node node){
        bottomFlowPane.getChildren().add(node);
    }

//    @FXML
//    protected void doSomething() {
//        System.out.println("The button was clicked!");
//    }
}