package ui.app.page.user.friend;

import exception.NotFoundException;
import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import persistence.entity.user.Friend;
import ui.app.component.field.labelfield.LabelField;
import ui.app.component.form.Form;

import java.net.URL;
import java.util.*;

public class FriendList implements Initializable, Observer {

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane friendTitledPane;

    @FXML
    private Form formController;

    @FXML
    private GridPane requestCardHolder, friendCardHolder;
    private ObservableList<FriendCard> friendCardList = FXCollections.observableArrayList();
    private ObservableList<FriendCard> requestCardList = FXCollections.observableArrayList();

    private final int nbColumn = 4;

    private static final String labelUsername = "Nom d'utilisateur";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRequestList();
        setFriendList();

        accordion.setExpandedPane(friendTitledPane);

        // Set form
        setForm();
    }

    private void setRequestList() {
        try {
            List<Friend> requestList = Facade.getInstance().getFriendRequestList();
            Collections.sort(requestList);

            requestCardList.clear();

            int maxWidth = 1280;
            int gapBetweenCard = 20;
            double preferredHeight = 230.0;
            double preferredWidth = (maxWidth - (nbColumn + 1) * gapBetweenCard) / nbColumn;

            requestList.forEach(friend -> requestCardList.add(new FriendCard(friend, preferredHeight, preferredWidth, true)));

            setCardHolderStyle(requestCardHolder);

            onSearch();
        } catch (NoLoggedUser ignored) { }
    }

    private void setFriendList() {
        try {
            List<Friend> friendList = Facade.getInstance().getFriendList(false);
            Collections.sort(friendList);

            friendCardList.clear();

            int maxWidth = 1280;
            int gapBetweenCard = 20;
            double preferredHeight = 230.0;
            double preferredWidth = (maxWidth - (nbColumn + 1) * gapBetweenCard) / nbColumn;

            friendList.forEach(friend -> friendCardList.add(new FriendCard(friend, preferredHeight, preferredWidth, false)));

            setCardHolderStyle(friendCardHolder);

            onSearch();
        } catch (NoLoggedUser ignored) { }
    }

    private void setForm() {
        // Set form
        formController.addObserver(this);
        formController.clearFieldList();

        formController.addField(new LabelField(labelUsername, true));

        formController.setSubmitButtonText("Ajouter");
        formController.initialize(null, null);
    }

    @FXML
    public void onSearch() {
        int count = 0;

        friendCardHolder.getChildren().clear();
        for (FriendCard card : friendCardList) {
            friendCardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }

        count = 0;

        requestCardHolder.getChildren().clear();
        for (FriendCard card : requestCardList) {
            requestCardHolder.add(card, count % nbColumn, count / nbColumn);
            count++;
        }
    }

    private void setCardHolderStyle(GridPane cardHolder) {
        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-padding:10px;");
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> labelFieldMap = (Map<String, Object>) arg;

        try {
            // The form is valid. Try to add the friend.
            Facade.getInstance()
                    .addFriend(labelFieldMap.get(labelUsername).toString());
            initialize(null, null);
        } catch (NoLoggedUser ignore) {
        } catch (NotFoundException e) {
            formController.showErrorLabel("L'utilisateur n'existe pas.");
        }
    }

    @FXML
    private void onClickFriendList() {
        setFriendList();
    }

    @FXML
    private void onClickRequestList() {
        setRequestList();
    }
}
