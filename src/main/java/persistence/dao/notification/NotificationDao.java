package persistence.dao.notification;

import constant.CollectionNames;
import org.bson.conversions.Bson;
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
		/// TODO : Add this constant in the file CollectionNames.java.
		return CollectionNames.NOTIFICATION;
	}

	@Override
	protected Class<Notification> getEntityClass() {
		return Notification.class;
	}

	@Override
	protected Bson getSetOnUpdate(Notification entity) {
		/// TODO : Set this method.
		List<Bson> updateList = new ArrayList<>();

		/// TODO : Do like this for attributes of the entity.
//		updateList.add(Updates.set("name", entity.getName()));

		/// TODO : Do like this for nullable attributes.
//		if (entity.getDescription() != null) {
//			// Nullable attribute.
//			updateList.add(Updates.set("description", entity.getDescription()));
//		}

		return combine(updateList);
	}
}
