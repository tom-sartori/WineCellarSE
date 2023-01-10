package logic.controller.event;

import logic.controller.AbstractController;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.dao.mongodb.event.EventMongoDao;
import persistence.entity.event.Event;

import java.util.List;

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
    protected AbstractMongoDao<Event> getDao() {
        return EventMongoDao.getInstance();
    }

    /**
     * Get events by their company id.
     *
     * @param company The id of the company.
     * @return A list of events.
     */
    public List<Event> findAllByCompany(ObjectId company) {
        BsonDocument filter = new BsonDocument();
        filter.append("company", new org.bson.BsonObjectId(company));
        return getDao().findAllWithFilter(filter);
    }
}
