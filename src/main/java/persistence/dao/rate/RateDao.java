package persistence.dao.rate;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.rate.Rate;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class RateDao extends AbstractDao<Rate> {

	private static RateDao instance;

	private RateDao() { }

	public static RateDao getInstance() {
		if (instance == null) {
			instance = new RateDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		/// TODO : Add this constant in the file CollectionNames.java.
		return CollectionNames.RATE;
	}

	@Override
	protected Class<Rate> getEntityClass() {
		return Rate.class;
	}

	@Override
	protected Bson getSetOnUpdate(Rate entity) {
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
