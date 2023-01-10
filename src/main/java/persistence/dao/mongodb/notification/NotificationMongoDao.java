package persistence.dao.mongodb.notification;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.notification.Notification;

import java.util.List;

/**
 * NotificationDao class extending Dao class parametrized with Notification class.
 */
public class NotificationMongoDao extends AbstractMongoDao<Notification> {

	/**
	 * Instance of NotificationDao to ensure Singleton design pattern.
	 */
	private static NotificationMongoDao instance;

	/**
	 * Private constructor for NotificationDao to ensure Singleton design pattern.
	 */
	private NotificationMongoDao() { }

	/**
	 * @return the instance of NotificationDao to ensure Singleton design pattern.
	 */
	public static NotificationMongoDao getInstance() {
		if (instance == null) {
			instance = new NotificationMongoDao();
		}
		return instance;
	}

	/**
	 * @return the constant name of the specific Collection (Notification).
	 */
	@Override
	protected String getCollectionName() {
		return CollectionNames.NOTIFICATION;
	}

	/**
	 * @return the class of the specific Entity (Notification).
	 */
	@Override
	protected Class<Notification> getEntityClass() {
		return Notification.class;
	}

	@Override
	protected Bson getSetOnUpdate(Notification entity) {
		return Updates.combine(
				Updates.set("message", entity.getMessage()),
				Updates.set("read", entity.isRead()),
				Updates.set("date", entity.getDate())
		);
	}

	//methodes annexes

	/**
	 * Get all notifications of a user.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the notifications of the user.
	 */
	public List<Notification> getNotificationListFromUser(ObjectId userId) throws Exception {
		BsonDocument filter = new BsonDocument();
		filter.append("ownerRef", new org.bson.BsonObjectId(userId));
		return findAllWithFilter(filter);
	}
}
