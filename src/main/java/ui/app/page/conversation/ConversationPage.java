package ui.app.page.conversation;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.bson.types.ObjectId;
import persistence.entity.conversation.Conversation;
import persistence.entity.user.User;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ConversationPage implements Initializable {

    @FXML
    private ScrollPane leftScrollPane, rightScrollBar;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea sendMessage;

    private Conversation selectedConversation;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.selectedConversation = null;
            rightScrollBar.setContent(new VBox());

            List<Conversation> conversationList = Facade.getInstance().getConversationList();
            Collections.sort(conversationList);

            setLeftScrollPane(conversationList);

            sendMessage.setVisible(false);
            sendButton.setVisible(false);
        } catch (NoLoggedUser ignore) { }
    }

    private void setLeftScrollPane(List<Conversation> conversationList) {
        leftScrollPane.setFitToWidth(true);
        VBox vBox = new VBox();
        conversationList.forEach(conversation -> {
            Button button = new Button(getName(conversation));
            button.setPrefHeight(50);
            button.setPrefWidth(350);
            button.getStyleClass().add("left-button");
            button.setOnMouseClicked(event -> {
                setRightScrollBar(conversation.getId());
            });

            vBox.getChildren().add(button);
        });

        vBox.setSpacing(10);
        leftScrollPane.setContent(vBox);
    }

    private void setLeftScrollPane() {
        List<Conversation> conversationList;
        try {
            conversationList = Facade.getInstance().getConversationList();
            Collections.sort(conversationList);
            setLeftScrollPane(conversationList);
        } catch (NoLoggedUser ignore) { }
    }

    public void setRightScrollBar(ObjectId conversationId) {
        Conversation conversation = Facade.getInstance().findOneConversation(conversationId);
        this.selectedConversation = conversation;

        rightScrollBar.setFitToWidth(true);
        sendMessage.setVisible(true);
        sendButton.setVisible(true);

        GridPane gridPane = new GridPane();
        gridPane.prefWidth(930);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        ColumnConstraints column1 = new ColumnConstraints(880./2);
        ColumnConstraints column2 = new ColumnConstraints(880./2);
        gridPane.getColumnConstraints().addAll(column1, column2);

        User loggedUser = null;
        try {
            loggedUser = Facade.getInstance().getLoggedUser();
        } catch (NoLoggedUser ignore) { }
        User finalLoggedUser = loggedUser;

        Collections.sort(conversation.getMessages());
        conversation.getMessages().forEach(message -> {
            String labelText = (! message.getSender().equals(finalLoggedUser.getUsername()) && conversation.getUsernames().size() > 2) ?
                    message.getSender() + ": " + message.getMessage() :
                    message.getMessage();
            Label label = new Label(labelText);

            // Set label style
            label.setWrapText(true);
            label.getStyleClass().add("message");

            try {
                if (message.getSender().equals(Facade.getInstance().getLoggedUser().getUsername())) {
                    gridPane.add(label, 1, conversation.getMessages().size() - conversation.getMessages().indexOf(message));
                    label.getStyleClass().add("message-sent");
                } else {
                    gridPane.add(label, 0, conversation.getMessages().size() - conversation.getMessages().indexOf(message));
                    label.getStyleClass().add("message-received");
                }
            } catch (NoLoggedUser ignore) { }
        });

        // Scroll to the bottom of the gridPane
        gridPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            rightScrollBar.setVvalue(1.0);
        });
        rightScrollBar.setContent(gridPane);
    }

    @FXML
    private void onSend() {
        if (selectedConversation != null && sendMessage.getText().length() != 0) {
            Facade.getInstance().sendMessage(selectedConversation.getId(), sendMessage.getText());
            sendMessage.setText("");
            setRightScrollBar(selectedConversation.getId());
        }
    }

    private String getName(Conversation conversation) {
        try {
            User loggedUser = Facade.getInstance().getLoggedUser();

            String name = conversation.getUsernames().stream()
                    .filter(username -> ! username.equals(loggedUser.getUsername()))
                    .reduce((s, s2) -> s + " - " + s2)
                    .orElse("");

            Date lastUpdate = conversation.getLastUpdate();
            Date now = new Date();
            long diff = (now.getTime() - lastUpdate.getTime()) / 1000;
            String date =
                    diff < 60 ? diff + "s" :
                            diff < 3600 ? diff / 60 + "m" :
                                    diff < (3600 * 24) ? diff / 3600 + "h" :
                                            diff / (3600 * 24) + "d";
            return name + " (" + date + ")";
        } catch (NoLoggedUser e) {
            return conversation.getId().toString();
        }
    }

    public void onClickRightScrollBar() {
        if (selectedConversation != null) {
            setRightScrollBar(selectedConversation.getId());
        }
        setLeftScrollPane();
    }
}
