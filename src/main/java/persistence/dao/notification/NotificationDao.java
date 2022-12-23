package persistence.dao.notification;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.AbstractDao;
import persistence.entity.notification.Notification;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class NotificationDao extends AbstractDao<Notification> {

	private static NotificationDao instance;

	private NotificationDao() { }

	public static NotificationDao getInstance() {
		if (instance == null) {
			instance = new NotificationDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.NOTIFICATION;
	}

	/**
	 * Get all notifications of a user.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the notifications of the user.
	 */
	public List<Notification> getNotificationFromUser(ObjectId userId) throws Exception {
		BsonDocument filter = new BsonDocument();
		filter.append("ownerRef", new org.bson.BsonObjectId(userId));
		return findAllWithFilter(filter);
	}

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
}
