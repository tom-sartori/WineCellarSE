package persistence.dao.mongodb.event;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.event.Event;

/**
 * EventDao class extending Dao class parametrized with Event class.
 */
public class EventMongoDao extends AbstractMongoDao<Event> {

	/**
	 * Instance of EventDao to ensure Singleton design pattern.
	 */
	private static EventMongoDao instance;

	/**
	 * Private constructor for EventDao to ensure Singleton design pattern.
	 */
	private EventMongoDao() { }

	/**
	 * @return the instance of EventDao to ensure Singleton design pattern.
	 */
	public static EventMongoDao getInstance() {
		if (instance == null) {
			instance = new EventMongoDao();
		}
		return instance;
	}

	/**
	 * @return the constant name of the specific Collection (Event).
	 */
	@Override
	protected String getCollectionName() {
		return CollectionNames.EVENT;
	}

	/**
	 * @return the class of the specific Entity (Event).
	 */
	@Override
	protected Class<Event> getEntityClass() {
		return Event.class;
	}

	@Override
	protected Bson getSetOnUpdate(Event entity) {
		return Updates.combine(
				Updates.set("name", entity.getName()),
				Updates.set("address", entity.getAddress()),
				Updates.set("company", entity.getCompany()),
				Updates.set("description", entity.getDescription()),
				Updates.set("startDate", entity.getStartDate()),
				Updates.set("endDate", entity.getEndDate())
		);
	}
}
