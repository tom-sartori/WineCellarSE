package ui.app.page.partner.list;

import facade.Facade;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import persistence.entity.partner.Partner;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.partner.detail.PartnerDetail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class PartnerCard extends Pane {

	protected final Label name;

	protected ImageView photo, photoDelete, photoEdit;

	private Partner partner;

	private final double preferredHeight, preferredWidth;

	private final int photoButtonSize;

	public PartnerCard(Partner partner, double preferredHeight, double preferredWidth) {
		this.partner = partner;
		this.preferredHeight = preferredHeight;
		this.preferredWidth = preferredWidth;
		this.photoButtonSize = 20;

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

		photo.setOnMouseClicked(e -> {
			// See detail view.
			PartnerDetail partnerDetail = (PartnerDetail) new CustomSceneHelper().getController("partnerDetail");
			partnerDetail.switchToDetail(partner);
			new CustomSceneHelper().getNodeById("partnerDetail").toFront();
		});

		getChildren().addAll(photo, name);

		if (Facade.getInstance().isAdminLogged()) {
			setAdminCard();
		}
	}

	private void setAdminCard() {
		setDeleteButton();
		setEditButton();
	}

	private void setDeleteButton() {
		photoDelete = new ImageView();
		try {
			photoDelete.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/trash.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		photoDelete.setLayoutX(40);
		photoDelete.setLayoutY(preferredHeight - photoButtonSize - 40);
		photoDelete.setFitHeight(photoButtonSize);
		photoDelete.setFitWidth(photoButtonSize);

		photoDelete.setOnMouseClicked(e -> {
			// Delete the partner.
			Facade.getInstance().deleteOnePartner(partner.getId());
			new CustomSceneHelper().bringNodeToFront("partnerList");
		});

		getChildren().add(photoDelete);
	}

	private void setEditButton() {
		photoEdit = new ImageView();
		try {
			photoEdit.setImage(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("../../../../assets/edit.png")).getPath())));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		photoEdit.setLayoutX(40);
		photoEdit.setLayoutY(photoDelete.getLayoutY() - photoButtonSize - 10);
		photoEdit.setFitHeight(photoButtonSize);
		photoEdit.setFitWidth(photoButtonSize);

		photoEdit.setOnMouseClicked(e -> {
			// Edit the partner.
			PartnerDetail partnerDetail = (PartnerDetail) new CustomSceneHelper().getController("partnerDetail");
			partnerDetail.switchToUpdate(partner);
			new CustomSceneHelper().getNodeById("partnerDetail").toFront();
		});

		getChildren().add(photoEdit);
	}
}
