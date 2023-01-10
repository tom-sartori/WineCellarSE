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
import persistence.entity.company.Company;
import persistence.entity.event.Event;
import ui.app.State;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.page.company.event.list.EventList;
import ui.app.page.company.event.update.EventUpdate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

public class EventCard extends Pane {
	private Event event1;

	@FXML
	private AnchorPane eventCard;
	@FXML
	private Button supprimer, update;

	@FXML
	private TextArea description;
	@FXML
	private Label name, address, startDate, endDate, nameCompany;

	private final CustomSceneHelper sceneHelper = new CustomSceneHelper();

	//TODO : bouton supprimer apparait pas automatiquement quand on se connecte

	public EventCard(Event event1) {

		this.event1 = event1;
		Company company = Facade.getInstance().getOneCompany(event1.getCompany());
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
		update = (Button) eventCard.lookup("#update");

		nameCompany.setText(company.getName());
		name.setText(event1.getName());
		address.setText(event1.getAddress());
		description.setText(event1.getDescription());
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		String endDate1 = pattern.format(event1.getEndDate());
		String startDate1 = pattern.format(event1.getStartDate());
		startDate.setText(startDate1);
		endDate.setText(endDate1);

		setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-alignment: center;");

		DropShadow dropShadow = new DropShadow();
		dropShadow.setHeight(3);
		dropShadow.setWidth(3);
		dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
		setEffect(dropShadow);

		if(Facade.getInstance().isManagerOfCompany(company.getId())){
			supprimer.setVisible(true);
			update.setVisible(true);
			supprimer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Voulez-vous réellement supprimer l'évènement " + event1.getName() + " ?");

					Optional<ButtonType> option = alert.showAndWait();

					if (option.get() == ButtonType.OK) {
						Facade.getInstance().deleteOneEvent(event1.getId());
					}
					sceneHelper.bringNodeToFront(EventList.class.getSimpleName());
				}

			});

			update.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					State.getInstance().setCurrentEvent(event1);
					sceneHelper.bringNodeToFront(EventUpdate.class.getSimpleName());
				}

			});

			getChildren().addAll(supprimer);
		}
		else {
			update.setVisible(false);
			supprimer.setVisible(false);
		}
	}
	public EventCard() {}

}
