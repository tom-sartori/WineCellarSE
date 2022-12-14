package logic.controller.event;

import logic.controller.AbstractController;
import persistence.dao.AbstractDao;
import persistence.dao.event.EventDao;
import persistence.entity.event.Event;

/**
 * EventController class extending Controller class parametrized with Event class.
 */
public class EventController extends AbstractController<Event> {

    /**
     * Instance of EventController to ensure Singleton design pattern.
     */
    private static EventController instance;

    /**
     * Private constructor for EventController to ensure Singleton design pattern.
     */
    private EventController() { }

    /**
     * @return the instance of EventController to ensure Singleton design pattern.
     */
    public static EventController getInstance() {
        if(instance == null){
            instance = new EventController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (EventDao).
     */
    @Override
    protected AbstractDao<Event> getDao() {
        return EventDao.getInstance();
    }
}
