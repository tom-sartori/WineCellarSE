package ui.app.page.user.friend;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import org.bson.types.ObjectId;
import persistence.entity.conversation.Conversation;
import persistence.entity.user.Friend;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.conversation.ConversationPage;
import ui.app.page.partner.detail.PartnerDetail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class FriendCard extends Pane {

	protected final Label name;

	protected ImageView photo, photoDelete, photoValidate;

	private Friend friend;

	private final double preferredHeight, preferredWidth;

	private final int photoSize;

	public FriendCard(Friend friend, double preferredHeight, double preferredWidth, boolean isRequestList) {
		this.friend = friend;
		this.preferredHeight = preferredHeight;
		this.preferredWidth = preferredWidth;
		this.photoSize = 20;

		photo = new ImageView();
		name = new Label();

		setPrefHeight(preferredHeight);
		setPrefWidth(preferredWidth);
		setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center;");

		DropShadow dropShadow = new DropShadow();
		dropShadow.setHeight(3);
		dropShadow.setWidth(3);
		dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
		setEffect(dropShadow);

		name.setAlignment(Pos.CENTER);
		name.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
		name.setLayoutX(0);
		name.setLayoutY(40);
		name.setPrefHeight(26.0);
		name.setPrefWidth(preferredWidth);
		name.setText(friend.getUsername());
		name.setFont(new Font(17.0));

		try {
			photo.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/user/profile.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		int photoSize = 80;
		photo.setLayoutX((preferredWidth - photoSize) / 2);
		photo.setLayoutY(preferredHeight - photoSize - 40);
		photo.setFitHeight(photoSize);
		photo.setFitWidth(photoSize);


		if (! friend.isAccepted()) {
			Label status = new Label("En attente");
			status.setAlignment(Pos.CENTER);
			status.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
			status.setLayoutX(0);
			status.setLayoutY(60);
			status.setStyle("-fx-text-fill: #FF0000;");
			status.setPrefHeight(26.0);
			status.setPrefWidth(preferredWidth);
			status.setFont(new Font(15.0));

			getChildren().add(status);
		}


		if (Facade.getInstance().isUserLogged()) {
			photo.setOnMouseClicked(e -> {
				// Create a conversation.
				try {
					ObjectId conversationId = Facade.getInstance().insertOneConversation(new Conversation(Facade.getInstance().getLoggedUser().getUsername(), friend.getUsername()));
					new CustomSceneHelper().bringNodeToFront("conversationPage");
					ConversationPage conversationPage = (ConversationPage) new CustomSceneHelper().getController("conversationPage");
					conversationPage.setRightScrollBar(conversationId);
				} catch (NoLoggedUser ignore) {
				}
			});
		}

		getChildren().addAll(photo, name);


		if (isRequestList) {
			// Add accept and refuse buttons.
			setRequestCard();
		}
		else {
			setDeleteButton();
		}
	}

	private void setRequestCard() {
		setDeleteButton();
		setAcceptButton();
	}

	// Set delete button
	public void setDeleteButton() {
		photoDelete = new ImageView();
		try {
			photoDelete.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/trash.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		photoDelete.setLayoutX(40);
		photoDelete.setLayoutY(preferredHeight - photoSize - 40);
		photoDelete.setFitHeight(photoSize);
		photoDelete.setFitWidth(photoSize);

		photoDelete.setOnMouseClicked(e -> {
			// Delete the friend request.
			try {
				Facade.getInstance().removeFriend(friend.getUsername());
			} catch (NoLoggedUser ignore) { }
		});

		getChildren().add(photoDelete);
	}

	public void setAcceptButton() {
		photoValidate = new ImageView();
		try {
			photoValidate.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/validate.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		photoValidate.setLayoutX(40);
		photoValidate.setLayoutY(photoDelete.getLayoutY() - photoSize - 10);
		photoValidate.setFitHeight(photoSize);
		photoValidate.setFitWidth(photoSize);

		photoValidate.setOnMouseClicked(e -> {
			// Accept the friend request.
			try {
				Facade.getInstance().acceptFriend(friend.getUsername());
			} catch (NoLoggedUser ignore) { }
		});

		getChildren().add(photoValidate);
	}
}
