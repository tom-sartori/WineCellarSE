package ui.app.page.company.advertising;

import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import persistence.entity.advertising.Advertising;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.advertising.details.AdvertisingDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class AdvertisingCard extends Pane {

	protected final Label name;

	protected ImageView photo, photoDelete, photoEdit;

	private Advertising advertising;

	private Button supprimer, action2;

	private final double preferredHeight, preferredWidth;

	private final CustomSceneHelper sceneHelper;

	private String previousPage;

	public AdvertisingCard(Advertising advertising, double preferredHeight, double preferredWidth, String previousPage) {
		this.advertising = advertising;
		this.preferredHeight = preferredHeight;
		this.preferredWidth = preferredWidth;
		this.previousPage = previousPage;
		sceneHelper = new CustomSceneHelper();

		photo = new ImageView();
		name = new Label();
		supprimer = new Button("supprimer");

		if (State.getInstance().getCurrentUser() != null) {
			action2 = new Button("valider");

			/**
			 * Validate the advertising and create an alert.
			 */
			action2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Facade.getInstance().validateAdvertising(advertising.getId());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Validation");
					alert.setHeaderText(null);
					alert.setContentText(advertising.getName() + " est validé !");

					Optional<ButtonType> option = alert.showAndWait();
				}
			});
		} else {
			action2 = new Button("renouveler");

			/**
			 * Create the renewal alert to enter the new date.
			 * Check the validity of the new date and renew the advertising.
			 */
			action2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Date previousDate = advertising.getEndDate();
					TextInputDialog dialog = new TextInputDialog();

					dialog.setTitle("Renouveler la publicité");
					dialog.setHeaderText("Entrez la nouvelle date de fin:");
					dialog.setContentText("Date de fin :");

					Optional<String> result = dialog.showAndWait();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initModality(Modality.APPLICATION_MODAL);

					result.ifPresent(date -> {
						try {
							Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
							Date now = new Date();
							if (endDate.before(now) || endDate.before(advertising.getEndDate())) {
								alert.setContentText("La nouvelle date n'est pas valide");
								alert.showAndWait();
							}
							else {
								createPriceAlert(previousDate);
							}
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
					});
				}
			});
		}

		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Voulez-vous réellement supprimer "+advertising.getName()+" ?");

				Optional<ButtonType> option = alert.showAndWait();

				if (option.get() == ButtonType.OK) {
					Facade.getInstance().deleteOneAdvertising(advertising.getId());
				}
			}
		});

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
		name.setText(advertising.getName());
		name.setFont(new Font(17.0));

		supprimer.setLayoutX(70);
		supprimer.setLayoutY(200);

		action2.setLayoutX(160);
		action2.setLayoutY(200);

		try {
			photo.setImage(new Image(new URL(advertising.getLink()).openStream()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		int photoSize = 80;
		photo.setLayoutX((preferredWidth - photoSize) / 2);
		photo.setLayoutY(preferredHeight - photoSize - 40);
		photo.setFitHeight(photoSize);
		photo.setFitWidth(photoSize);

		/**
		 * Save the current advertising for the Details page.
		 * Save the list from which was the advertising.
		 * Show the Details page with the details of the advertising.
		 */
		setOnMouseClicked(e -> {
			State.getInstance().setCurrentAdvertising(advertising);
			State.getInstance().setPreviousPage(previousPage);
			this.sceneHelper.bringNodeToFront(AdvertisingDetails.class.getSimpleName());
		});

		getChildren().addAll(photo, name, supprimer, action2);
	}

	public AdvertisingCard(Advertising advertising, String previousPage) {
		this(advertising, 230.0, 230.0, previousPage);
	}

	/**
	 * Create the price alert by calculating the price of the advertising.
	 * @param endDate
	 */
	public void createPriceAlert(Date endDate){
		Date now = new Date();
		String price;
		if(now.before(advertising.getEndDate())){
			price = String.valueOf(Facade.getInstance().calculatePriceAdvertising(advertising.getEndDate(), endDate));
		} else {
			price = String.valueOf(Facade.getInstance().calculatePriceAdvertising(now, endDate));
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Prix total");
		alert.setHeaderText("Le prix calculé de cette publicité est : ");
		alert.setContentText(price + " €");

		Optional<ButtonType> option = alert.showAndWait();

		if (option.get() == ButtonType.OK) {
			Facade.getInstance().renewOneAdvertising(advertising.getId(), endDate);
			Facade.getInstance().payOneAdvertising(advertising.getId());
		}
	}
}
