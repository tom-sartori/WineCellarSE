package ui.controllers.contentarea;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import ui.app.businesslogic.MainBotRoutine;
import ui.app.supportingthreads.GlobalKeyListener;
import ui.helpers.services.LoggingService;
import ui.helpers.services.RobotService;

public class HomePageController implements Initializable
{
    LoggingService log = new LoggingService();
    RobotService bot;
    private static boolean hasStarted = false;
    
    public HomePageController()
    {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
								if (!hasStarted)
								{
												try 
												{
																log.appendToEventLogsFile("ApplicationStarted. ");
																log.appendToApplicationLogsFile("ApplicationStarted. ");
												} catch (FileNotFoundException ex) {Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);}
								}
								hasStarted = true;
    }    
    
    @FXML
    public void startApplication(MouseEvent event) throws InterruptedException, AWTException 
    {
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
