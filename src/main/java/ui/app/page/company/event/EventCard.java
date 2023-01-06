package ui.app.page.company.event;

import facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistence.entity.event.Event;
import ui.app.State;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

public class EventCard extends Pane {
	private Event event;

	@FXML
	private AnchorPane eventCard;
	@FXML
	private Button supprimer;

	@FXML
	private TextArea description;
	@FXML
	private Label name, address, startDate, endDate, nameCompany;

	//TODO : Bouton supprimer pour managers et admin
	public EventCard(Event event) {
		this.event = event;

		try {
			eventCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EventCard.fxml")));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		getChildren().add(eventCard);

		name = (Label) eventCard.lookup("#name");
		nameCompany = (Label) eventCard.lookup("#nameCompany");
		address = (Label) eventCard.lookup("#address");
		description = (TextArea) eventCard.lookup("#description");
		startDate = (Label) eventCard.lookup("#startDate");
		endDate = (Label) eventCard.lookup("#endDate");
		supprimer = (Button) eventCard.lookup("#supprimer");

		nameCompany.setText(Facade.getInstance().getOneCompany(event.getCompany()).getName());

		name.setText(event.getName());
		address.setText(event.getAddress());
		description.setText(event.getDescription());
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		String endDate1 = pattern.format(event.getEndDate());
		String startDate1 = pattern.format(event.getStartDate());
		startDate.setText(startDate1);
		endDate.setText(endDate1);

		supprimer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event1) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Voulez-vous réellement supprimer l'évènement " + event.getName() + " ?");

				Optional<ButtonType> option = alert.showAndWait();

				if (option.get() == ButtonType.OK) {
					Facade.getInstance().deleteOneReferencing(event.getId());
				}
			}
		});

		setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center;");

		DropShadow dropShadow = new DropShadow();
		dropShadow.setHeight(3);
		dropShadow.setWidth(3);
		dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
		setEffect(dropShadow);

		getChildren().addAll(supprimer);


		if (State.getInstance().getCurrentUser() != null) {
		}
	}
	public EventCard() {}

}
