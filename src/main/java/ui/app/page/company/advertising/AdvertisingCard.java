package ui.app.page.company.advertising;

import exception.user.NoLoggedUser;
import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import persistence.entity.advertising.Advertising;
import ui.app.State;
import ui.app.helpers.CustomSceneHelper;
import ui.app.page.company.advertising.details.AdvertisingDetails;
import ui.app.page.company.advertising.list.AdvertisingList;
import ui.app.page.company.advertising.update.AdvertisingUpdate;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class AdvertisingCard extends Pane {

	@FXML
	private Label name, nameCompany;

	@FXML
	private ImageView image;

	private Advertising advertising;

	@FXML
	private Button supprimer, action2, update;
	@FXML
	private AnchorPane advertisingCard;
	private CustomSceneHelper sceneHelper;

	private String previousPage;

	public AdvertisingCard(Advertising advertising, String previousPage) {
		this.advertising = advertising;
		this.previousPage = previousPage;
		State.getInstance().setPreviousPage(previousPage);
		sceneHelper = new CustomSceneHelper();

		try {
			advertisingCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdvertisingCard.fxml")));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		getChildren().add(advertisingCard);
		name = (Label) advertisingCard.lookup("#name");
		image = (ImageView) advertisingCard.lookup("#image");
		nameCompany = (Label) advertisingCard.lookup("#nameCompany");
		supprimer = (Button) advertisingCard.lookup("#supprimer");
		action2 = (Button) advertisingCard.lookup("#action2");
		update = (Button) advertisingCard.lookup("#update");

		name.setText(advertising.getName());
		nameCompany.setText(Facade.getInstance().getOneCompany(advertising.getCompany()).getName());
		supprimer.setVisible(false);
		action2.setVisible(false);
		update.setVisible(false);

		try {
			if (Facade.getInstance().getLoggedUser().isAdmin()) {
				update.setVisible(false);
				action2.setText("Valider");
				supprimer.setVisible(true);
				action2.setVisible(true);

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
						sceneHelper.bringNodeToFront(AdvertisingList.class.getSimpleName());
					}
				});

				/**
				 * Update the advertising and create an alert.
				 */
				update.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						State.getInstance().setCurrentAdvertising(advertising);
						sceneHelper.bringNodeToFront(AdvertisingUpdate.class.getSimpleName());
					}
				});
			} else {
				update.setVisible(false);
				action2.setText("Renouveler");

				/**
				 * Create the renewal alert to enter the new date.
				 * Check the validity of the new date and renew the advertising.
				 */

				if(Facade.getInstance().isUserLogged()){
					action2.setVisible(true);
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
							sceneHelper.bringNodeToFront(AdvertisingList.class.getSimpleName());
						}
					});
				}
			}
		} catch (NoLoggedUser ignore) { }


		if(Facade.getInstance().isUserLogged()) {
			supprimer.setVisible(true);
			supprimer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Voulez-vous réellement supprimer "+advertising.getName()+" ?");

					Optional<ButtonType> option = alert.showAndWait();

					if (option.get() == ButtonType.OK) {
						Facade.getInstance().deleteOneAdvertising(advertising.getId());
					}
					sceneHelper.bringNodeToFront(AdvertisingList.class.getSimpleName());
				}
			});
		}

		setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center;");

		DropShadow dropShadow = new DropShadow();
		dropShadow.setHeight(3);
		dropShadow.setWidth(3);
		dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
		setEffect(dropShadow);

		try {
			image.setImage(new Image(new URL(advertising.getLink()).openStream()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

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

		getChildren().addAll(image, name, supprimer, action2);
	}

	public AdvertisingCard() {}

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
		Alert alert = new Alert(AlertType.CONFIRMATION);
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
