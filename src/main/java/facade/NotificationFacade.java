package facade;

import logic.controller.notification.NotificationController;
import org.bson.types.ObjectId;
import persistence.entity.notification.Notification;

import java.util.List;

/**
 * Specific facade for Notifications.
 */
class NotificationFacade {
    /// TODO : Be sure that you need every methods which are generated in this class. If not, remove them.
    /// TODO : After that, update Facade and FacadeInterface with those methods.

    /**
     * Singleton instance.
     */
    private static NotificationFacade instance;

    private NotificationFacade() { }

    /**
     * Get the singleton instance of the notification facade.
     *
     * @return The singleton instance.
     */
    public static NotificationFacade getInstance() {
        if (instance == null) {
            instance = new NotificationFacade();
        }
        return instance;
    }

    /**
     * Insert a notification.
     *
     * @param notification The notification to insert.
     * @return The id of the inserted notification.
     */
    protected ObjectId insertOneNotification(Notification notification) {
        return NotificationController.getInstance().insertOne(notification);
    }

    /**
     * Get all notifications.
     *
     * @return A list of notifications.
     */
    protected List<Notification> getNotificationList() {
        return NotificationController.getInstance().findAll();
    }

    /**
     * Get a notification by its id.
     *
     * @param id The id of the notification.
     * @return The notification or null if not found.
     */
    protected Notification getOneNotification(ObjectId id) {
        return NotificationController.getInstance().findOne(id);
    }

    /**
     * Update a notification.
     *
     * @param id The id of the notification to update.
     * @param notification The new notification.
     * @return true if the notification has been updated, false otherwise.
     */
    protected boolean updateOneNotification(ObjectId id, Notification notification) {
        return NotificationController.getInstance().updateOne(id, notification);
    }

    /**
     * Delete a notification.
     *
     * @param id The id of the notification to delete.
     * @return true if the notification has been deleted, false otherwise.
     */
    protected boolean deleteOneNotification(ObjectId id) {
        return NotificationController.getInstance().deleteOne(id);
    }
}
