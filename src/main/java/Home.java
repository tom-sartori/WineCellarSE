import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Home {

	private Group group;

	public Home(Group group) {
		this.group = group;
	}

	@FXML
	public void onButton() {
		System.out.println("Login button pressed");
	}

	public Parent getParent() throws IOException {
		FXMLLoader loader = new FXMLLoader();

//		loader.setLocation(new File("./Home.fxml").toURI().toURL());
//		return loader.load();
		return FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home.fxml")));
	}
}
