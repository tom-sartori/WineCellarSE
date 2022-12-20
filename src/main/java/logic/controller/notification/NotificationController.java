package logic.controller.notification;

import logic.controller.AbstractController;
import persistence.dao.notification.NotificationDao;
import persistence.entity.notification.Notification;

/**
 * NotificationController class extending Controller class parametrized with Notification class.
 */
public class NotificationController extends AbstractController<Notification> {

    /**
     * Instance of NotificationController to ensure Singleton design pattern.
     */
    private static NotificationController instance;

    /**
     * Private constructor for NotificationController to ensure Singleton design pattern.
     */
    private NotificationController() { }

    /**
     * @return the instance of NotificationController to ensure Singleton design pattern.
     */
    public static NotificationController getInstance() {
        if(instance == null){
            instance = new NotificationController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (NotificationDao).
     */
    @Override
    protected NotificationDao getDao() {
        return NotificationDao.getInstance();
    }
}
