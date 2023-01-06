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

    private final Facade facade = Facade.getInstance();
    private Event event;

    @BeforeEach
    void init() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.JANUARY,15);
        Date debut = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2022, Calendar.JANUARY,17);
        Date fin = cal2.getTime();
        ObjectId company = new ObjectId("63a5c45048f0c9194a9295ef");
        event = new Event("Halloween", "12 rue des Jardins 34170 Castelnau", "Event", debut, fin, company);
    }

    /**
     *  Test if event can be created.
     */
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

    /**
     *  Test if all events can be found.
     */
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

    /**
     *  Test if event can be found.
     */
    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedEvent = facade.insertOneEvent(event);

        Event receivedEvent = facade.getOneEvent(idOfInsertedEvent);

        assertEquals(event.getName(), receivedEvent.getName());
        assertEquals(event.getDescription(), receivedEvent.getDescription());
        assertEquals(event.getStartDate(), receivedEvent.getStartDate());
        assertEquals(event.getEndDate(), receivedEvent.getEndDate());
    }

    /**
     *  Test if event can be updated.
     */
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

    /**
     *  Test if event can be deleted.
     */
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

    /**
     *  Test if event can be found thanks to the company id.
     */
    @Test
    void test_findByCompany_OK() {
        ObjectId company = new ObjectId("63a5ce5496a16445da19e223");
        ObjectId company2 = new ObjectId("63a5ce5496a16445da19e224");
        event.setCompany(company);
        ObjectId idOfInsertedEvent = facade.insertOneEvent(event);
        event.setCompany(company2);
        ObjectId idOfInsertedEvent2 = facade.insertOneEvent(event);
        ObjectId idOfInsertedEvent3 = facade.insertOneEvent(event);

        List<Event> eventList = facade.getEventsByCompany(company);

        assertEquals(eventList.size(), 1);
        facade.deleteOneEvent(idOfInsertedEvent);
        facade.deleteOneEvent(idOfInsertedEvent2);
        facade.deleteOneEvent(idOfInsertedEvent3);
    }
}
