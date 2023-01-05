package facade;

import logic.controller.event.EventController;
import logic.controller.referencing.ReferencingController;
import org.bson.types.ObjectId;
import persistence.entity.event.Event;
import persistence.entity.referencing.Referencing;

import java.util.List;

/**
 * Specific facade for Events.
 */
class EventFacade {
    /**
     * Singleton instance.
     */
    private static EventFacade instance;

    private EventFacade() { }

    /**
     * Get the singleton instance of the event facade.
     *
     * @return The singleton instance.
     */
    public static EventFacade getInstance() {
        if (instance == null) {
            instance = new EventFacade();
        }
        return instance;
    }

    /**
     * Get all events.
     *
     * @return A list of events.
     */
    protected List<Event> getEventList() {
        return EventController.getInstance().findAll();
    }

    /**
     * Insert an event.
     *
     * @param event The event to insert.
     * @return The id of the inserted event.
     */
    protected ObjectId insertOneEvent(Event event) {
        return EventController.getInstance().insertOne(event);
    }

    /**
     * Get an event by its id.
     *
     * @param id The id of the event.
     * @return The event or null if not found.
     */
    protected Event getOneEvent(ObjectId id) {
        return EventController.getInstance().findOne(id);
    }

    /**
     * Update an event.
     *
     * @param id The id of the event to update.
     * @param event The new event.
     * @return true if the event has been updated, false otherwise.
     */
    protected boolean updateOneEvent(ObjectId id, Event event) {
        return EventController.getInstance().updateOne(id, event);
    }

    /**
     * Delete an event.
     *
     * @param id The id of the event to delete.
     * @return true if the event has been deleted, false otherwise.
     */
    protected boolean deleteOneEvent(ObjectId id) {
        return EventController.getInstance().deleteOne(id);
    }

    /**
     * Get events by their company id.
     *
     * @param company The id of the company.
     * @return A list of events.
     */
    protected List<Event> getEventsByCompany(ObjectId company) {
        return EventController.getInstance().findAllByCompany(company);
    }
}
