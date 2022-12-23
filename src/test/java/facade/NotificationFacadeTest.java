package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.notification.Notification;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationFacadeTest {
    private final FacadeInterface facade = Facade.getInstance();
    private Notification notification;

    @BeforeEach
    void init() {
        ObjectId idSubject = new ObjectId("6390648cc25c501853ae4d7c");
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,13);
        Date date = cal.getTime();
        notification = new Notification(idSubject, "nouvelle demande d'ami", false, date);
    }

    @Test
    void test_create_OK() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        notification.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneNotification(notification);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Notification receivedNotification = facade.getOneNotification(idReceivedAfterCreation);
        assertEquals(notification.getMessage(), receivedNotification.getMessage());
        assertEquals(notification.isRead(), receivedNotification.isRead());
        assertEquals(notification.getDate(), receivedNotification.getDate());
    }

    @Test
    void test_findAll_OK() {
        int initialNumberOfEvents = facade.getNotificationList().size();

        ObjectId idOfInsertedRate = facade.insertOneNotification(notification);
        facade.insertOneNotification(notification);
        facade.insertOneNotification(notification);

        List<Notification> receivedNotificationList = facade.getNotificationList();

        assertEquals(3 + initialNumberOfEvents, receivedNotificationList.size());
        assertTrue(receivedNotificationList.contains(facade.getOneNotification(idOfInsertedRate)));
    }

    /**
     * Test the getNotificationFromUser method.
     */
    @Test
    void getNotificationFromUser(){
        try {
            ObjectId userId = notification.getOwnerRef();

            ObjectId notificationId = facade.insertOneNotification(notification);

            List<Notification> notificationFromUserBefore = facade.getNotificationFromUser(userId);

            int sizeBefore = notificationFromUserBefore.size();

            ObjectId notificationId1 = facade.insertOneNotification(notification);

            List<Notification> notificationFromUserAfter = facade.getNotificationFromUser(userId);

            int sizeAfter = notificationFromUserAfter.size();

            assertEquals(sizeBefore + 1, sizeAfter);

            // CLEAN UP

            facade.deleteOneNotification(notificationId);
            facade.deleteOneNotification(notificationId1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedNotification = facade.insertOneNotification(notification);

        Notification receivedNotification = facade.getOneNotification(idOfInsertedNotification);

        assertEquals(notification.getMessage(), receivedNotification.getMessage());
        assertEquals(notification.getDate(), receivedNotification.getDate());
        assertEquals(notification.getOwnerRef(), receivedNotification.getOwnerRef());
        assertEquals(notification.isRead(), receivedNotification.isRead());
    }

    @Test
    void test_update_OK() {
        ObjectId idOfInsertedNotification = facade.insertOneNotification(notification);

        Notification receivedNotification = facade.getOneNotification(idOfInsertedNotification);
        receivedNotification.setMessage("demande acceptée");
        receivedNotification.setRead(true);

        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,23);
        Date date = cal.getTime();
        receivedNotification.setDate(date);

        facade.updateOneNotification(receivedNotification.getId(), receivedNotification);

        Notification updatedNotification = facade.getOneNotification(idOfInsertedNotification);

        assertEquals(receivedNotification.getMessage(), updatedNotification.getMessage());
        assertEquals(receivedNotification.getDate(), updatedNotification.getDate());
        assertEquals(true, updatedNotification.isRead());
    }

    @Test
    void test_delete_OK() {
        ObjectId notificationIdInserted = facade.insertOneNotification(notification);
        notification.setId(notificationIdInserted);
        List<Notification> notificationList = facade.getNotificationList();
        int initialNumberOfNotification = notificationList.size();
        assertTrue(notificationList.contains(facade.getOneNotification(notificationIdInserted)));

        assertTrue(facade.deleteOneNotification(notificationIdInserted));
        assertEquals(initialNumberOfNotification - 1, facade.getNotificationList().size());
    }
}
