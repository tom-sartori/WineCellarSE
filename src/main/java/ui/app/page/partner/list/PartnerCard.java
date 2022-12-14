package ui.app.page.partner.list;

import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import persistence.entity.partner.Partner;
import ui.app.State;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class PartnerCard extends Pane {

	protected final Label name;

	protected ImageView photo, photoDelete, photoEdit;

	private Partner partner;

	private final double preferredHeight, preferredWidth;

	public PartnerCard(Partner partner, double preferredHeight, double preferredWidth) {
		this.partner = partner;
		this.preferredHeight = preferredHeight;
		this.preferredWidth = preferredWidth;

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
		name.setText(partner.getName());
		name.setFont(new Font(17.0));

		try {
			photo.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/partner/type/" + partner.getType() + ".png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		int photoSize = 80;
		photo.setLayoutX((preferredWidth - photoSize) / 2);
		photo.setLayoutY(preferredHeight - photoSize - 40);
		photo.setFitHeight(photoSize);
		photo.setFitWidth(photoSize);

		setOnMouseClicked(e -> {
			// Action you want to do
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("Sample Alert");
			alert.showAndWait();
		});

		getChildren().addAll(photo, name);


		if (State.getInstance().getCurrentUser() != null) {
			setAdminCard();
		}
	}

	public PartnerCard(Partner partner) {
		this(partner, 230.0, 230.0);
	}

	private void setAdminCard() {
		int photoDeleteEditSize = 20;

		photoDelete = new ImageView();
		try {
			photoDelete.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/trash.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		photoDelete.setLayoutX(40);
		photoDelete.setLayoutY(preferredHeight - photoDeleteEditSize - 40);
		photoDelete.setFitHeight(photoDeleteEditSize);
		photoDelete.setFitWidth(photoDeleteEditSize);


		photoEdit = new ImageView();
		try {
			photoEdit.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/edit.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		photoEdit.setLayoutX(40);
		photoEdit.setLayoutY(photoDelete.getLayoutY() - photoDeleteEditSize - 10);
		photoEdit.setFitHeight(photoDeleteEditSize);
		photoEdit.setFitWidth(photoDeleteEditSize);

		getChildren().addAll(photoDelete, photoEdit);
	}
}
