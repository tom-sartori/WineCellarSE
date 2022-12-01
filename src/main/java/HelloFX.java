import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloFX extends Application {


	@Override
	public void start(Stage stage) throws IOException {
		// set title for the stage
        stage.setTitle("creating buttons");

		// create a stack pane
		StackPane r = new StackPane();

		// create a scene
		Scene sc = new Scene(r, 200, 200);

		Button b = new Button("button");
		b.getStyleClass().add("add");
		// add button
		r.getChildren().add(b);
		stage.setScene(sc);
		stage.show();
		Group group = new Group();


		Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
//		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Untitled.fxml")));

//        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello2.fxml")));

//		group.getChildren().add(root2);
//		group.getChildren().add(root);




		//group.getChildren().removeAll();

//		Home home = new Home(group);
		group.getChildren().add(root2);

		Scene scene = new Scene(group, 1280, 720);

		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}

//	@Override
//	public void start(Stage stage) throws IOException {
//		String javaVersion = System.getProperty("java.version");
//		String javafxVersion = System.getProperty("javafx.version");
//		Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//
//
//		Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
//
//		Group group = new Group();
//		group.getChildren().add(parent);
//
//		Scene scene = new Scene(group, 640, 480);
//		stage.setScene(scene);
//		stage.show();
//	}

	@FXML
	public void onButton() {
		System.out.println("Login button pressed");
	}


	public static void main(String[] args) {
		launch();
	}
}
