package ui.app.page.company.referencing;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistence.entity.referencing.Referencing;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.referencing.list.ReferencingList;
import ui.app.page.company.referencing.update.ReferencingUpdate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

public class ReferencingCard extends Pane {
	private Referencing referencing;

	@FXML
	private AnchorPane referencingCard;
	@FXML
	private Button supprimer, update;
	@FXML
	private Label status, paymentDate, level, startDate, endDate, price, nameCompany;

	private CustomSceneHelper sceneHelper = new CustomSceneHelper();

	public ReferencingCard(Referencing referencing) {
		this.referencing = referencing;

		try {
			referencingCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ReferencingCard.fxml")));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		getChildren().add(referencingCard);
		status = (Label) referencingCard.lookup("#status");
		nameCompany = (Label) referencingCard.lookup("#nameCompany");
		paymentDate = (Label) referencingCard.lookup("#paymentDate");
		level = (Label) referencingCard.lookup("#level");
		startDate = (Label) referencingCard.lookup("#startDate");
		endDate = (Label) referencingCard.lookup("#endDate");
		price = (Label) referencingCard.lookup("#price");
		supprimer = (Button) referencingCard.lookup("#supprimer");
		update = (Button) referencingCard.lookup("#update");

		status.setText(referencing.getStatus());
		nameCompany.setText(Facade.getInstance().getOneCompany(referencing.getCompany()).getName());
		price.setText(String.valueOf(referencing.getPrice())+" €");
		level.setText(String.valueOf(referencing.getImportanceLevel()));
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		String endDate1 = pattern.format(referencing.getExpirationDate());
		String startDate1 = pattern.format(referencing.getStartDate());
		String paymentDate1 = pattern.format(referencing.getPaymentDate());
		paymentDate.setText(paymentDate1);
		startDate.setText(startDate1);
		endDate.setText(endDate1);

		supprimer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Voulez-vous réellement supprimer ce référencement ?");

				Optional<ButtonType> option = alert.showAndWait();

				if (option.get() == ButtonType.OK) {
					Facade.getInstance().deleteOneReferencing(referencing.getId());
				}

				sceneHelper.bringNodeToFront(ReferencingList.class.getSimpleName());
			}
		});

		setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center;");

		DropShadow dropShadow = new DropShadow();
		dropShadow.setHeight(3);
		dropShadow.setWidth(3);
		dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
		setEffect(dropShadow);

		try {
			if (Facade.getInstance().getLoggedUser().isAdmin()) {
				update.setVisible(true);
				update.setVisible(true);
				/**
				 * Update the referencing and create an alert.
				 */
				update.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						State.getInstance().setCurrentReferencing(referencing);
						sceneHelper.bringNodeToFront(ReferencingUpdate.class.getSimpleName());
					}
				});
			} else {
				update.setVisible(false);
			}
		} catch (NoLoggedUser e) {
			throw new RuntimeException(e);
		}

		getChildren().addAll(supprimer);
	}

	public ReferencingCard() {}

}
