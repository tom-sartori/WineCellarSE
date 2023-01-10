package logic.controller.notification;

import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.notification.NotificationMongoDao;
import persistence.entity.notification.Notification;

import java.util.List;

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
    protected NotificationMongoDao getDao() {
        return NotificationMongoDao.getInstance();
    }


    /**
     * Get all the notifications of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the notifications of the user.
     */
    public List<Notification> getNotificationListFromUser(ObjectId userId) throws Exception {
        return NotificationMongoDao.getInstance().getNotificationListFromUser(userId);
    }
}
