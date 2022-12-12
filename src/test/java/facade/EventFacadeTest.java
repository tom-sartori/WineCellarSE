package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.event.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventFacadeTest {

    private final EventFacade facade = EventFacade.getInstance();
    private Event event;

    @BeforeEach
    void init() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.JANUARY,15);
        Date debut = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2022, Calendar.JANUARY,17);
        Date fin = cal2.getTime();
        event = new Event("Halloween", "Event", debut, fin);
    }

    @Test
    void test_create_OK() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        event.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneEvent(event);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Event receivedEvent = facade.getOneEvent(idReceivedAfterCreation);
        assertEquals(event.getName(), receivedEvent.getName());
        assertEquals(event.getDescription(), receivedEvent.getDescription());
    }

    @Test
    void test_findAll_OK() {
        int initialNumberOfEvents = facade.getEventList().size();

        ObjectId idOfInsertedEvent = facade.insertOneEvent(event);
        facade.insertOneEvent(event);
        facade.insertOneEvent(event);

        List<Event> receivedEventList = facade.getEventList();

        assertEquals(3 + initialNumberOfEvents, receivedEventList.size());
        assertTrue(receivedEventList.contains(facade.getOneEvent(idOfInsertedEvent)));
    }

    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedEvent = facade.insertOneEvent(event);

        Event receivedEvent = facade.getOneEvent(idOfInsertedEvent);

        assertEquals(event.getName(), receivedEvent.getName());
        assertEquals(event.getDescription(), receivedEvent.getDescription());
        assertEquals(event.getStartDate(), receivedEvent.getStartDate());
        assertEquals(event.getEndDate(), receivedEvent.getEndDate());
    }

    @Test
    void test_update_OK() {
        ObjectId idOfInsertedEvent = facade.insertOneEvent(event);

        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.JANUARY,18);
        Date debut = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2022, Calendar.JANUARY,19);
        Date fin = cal2.getTime();

        Event receivedEvent = facade.getOneEvent(idOfInsertedEvent);
        receivedEvent.setName("Halloween2");
        receivedEvent.setDescription("Test2");
        receivedEvent.setStartDate(debut);
        receivedEvent.setEndDate(fin);

        facade.updateOneEvent(receivedEvent.getId(), receivedEvent);

        Event updatedEvent = facade.getOneEvent(idOfInsertedEvent);

        assertEquals(receivedEvent.getName(), updatedEvent.getName());
        assertEquals(receivedEvent.getDescription(), updatedEvent.getDescription());
        assertEquals(receivedEvent.getStartDate(), updatedEvent.getStartDate());
        assertEquals(receivedEvent.getEndDate(), updatedEvent.getEndDate());
    }

    @Test
    void test_delete_OK() {
        ObjectId eventIdInserted = facade.insertOneEvent(event);
        event.setId(eventIdInserted);
        List<Event> eventList = facade.getEventList();
        int initialNumberOfEvents = eventList.size();
        assertTrue(eventList.contains(facade.getOneEvent(eventIdInserted)));

        assertTrue(facade.deleteOneEvent(eventIdInserted));
        assertEquals(initialNumberOfEvents - 1, facade.getEventList().size());
    }
}
