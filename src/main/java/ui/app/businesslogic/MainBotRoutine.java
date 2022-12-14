package ui.app.businesslogic;

import static ui.app.helpers.abstractsandenums.Duration.MEDIUM;

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ui.app.page.home.HomePage;
import ui.app.helpers.abstractsandenums.Routine;
import ui.app.helpers.services.CustomSceneHelper;
import ui.app.helpers.services.RobotService;

public class MainBotRoutine extends Routine {

	public void run() {
		synchronized (this) {
			try {
				RobotService bot = new RobotService();
				disableStartButton();
				while(running) {
					checkIfPausedOrStopped();
					System.out.println("Running");
					bot.delay(MEDIUM);
					checkIfPausedOrStopped();
				}
			}
			catch (AWTException ex) {
				Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (InterruptedException ex) {
				Logger.getLogger(MainBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void checkIfPausedOrStopped() throws InterruptedException {
		waitIfPaused();
		if (!running) {
			enableStartButton();
		}
	}

	private void disableStartButton() {
		CustomSceneHelper sceneHelper = new CustomSceneHelper();
		sceneHelper.getNodeById("startButton").setDisable(true);
	}

	private void enableStartButton() {
		CustomSceneHelper sceneHelper = new CustomSceneHelper();
		sceneHelper.getNodeById("startButton").setDisable(false);
	}
}
