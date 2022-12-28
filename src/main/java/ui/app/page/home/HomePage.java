package ui.app.page.home;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import ui.app.businesslogic.MainBotRoutine;
import ui.app.helpers.services.LoggingService;
import ui.app.helpers.services.RobotService;
import ui.app.supportingthreads.GlobalKeyListener;

import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePage implements Initializable {
	LoggingService log = new LoggingService();
	RobotService bot;
	private static boolean hasStarted = false;

	public HomePage() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (!hasStarted) {
			try {
				log.appendToEventLogsFile("ApplicationStarted. ");
				log.appendToApplicationLogsFile("ApplicationStarted. ");
			}
			catch (FileNotFoundException ex) {
				Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		hasStarted = true;
	}

	@FXML
	public void startApplication(MouseEvent event) throws InterruptedException, AWTException {
		MainBotRoutine mainBotRoutine = new MainBotRoutine();
		mainBotRoutine.setDaemon(true);
		mainBotRoutine.start();

		GlobalKeyListener globalKeyListener = new GlobalKeyListener(mainBotRoutine);
		globalKeyListener.setDaemon(true);
		globalKeyListener.start();

		//botRoutine.join();
		//System.out.println("Remember to re-active button. ");
	}
}
