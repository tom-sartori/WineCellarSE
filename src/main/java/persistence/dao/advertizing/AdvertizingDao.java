package persistence.dao.advertizing;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.advertizing.Advertizing;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class AdvertizingDao extends AbstractDao<Advertizing> {

	private static AdvertizingDao instance;

	private AdvertizingDao() { }

	public static AdvertizingDao getInstance() {
		if (instance == null) {
			instance = new AdvertizingDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		/// TODO : Add this constant in the file CollectionNames.java.
		return CollectionNames.ADVERTIZING;
	}

	@Override
	protected Class<Advertizing> getEntityClass() {
		return Advertizing.class;
	}

	@Override
	protected Bson getSetOnUpdate(Advertizing entity) {
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
