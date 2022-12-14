package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Starter extends Application {
	private static Stage stage;
	private static Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		stage.initStyle(StageStyle.UNDECORATED);
		setPrimaryStage(stage);
		setPrimaryScene(scene);
		Parent root = FXMLLoader.load(getClass().getResource("app/theme/main/Main.fxml"));

		scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void setPrimaryStage(Stage stage) {
		Starter.stage = stage;
	}

	public static Stage getMainStage() {
		return Starter.stage;
	}

	private void setPrimaryScene(Scene scene) {
		Starter.scene = scene;
	}

	public static Scene getMainScene() {
		return Starter.scene;
	}
}
