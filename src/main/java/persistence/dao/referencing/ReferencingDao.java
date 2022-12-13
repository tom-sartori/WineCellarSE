package persistence.dao.referencing;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.referencing.Referencing;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class ReferencingDao extends AbstractDao<Referencing> {

	private static ReferencingDao instance;

	private ReferencingDao() { }

	public static ReferencingDao getInstance() {
		if (instance == null) {
			instance = new ReferencingDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		/// TODO : Add this constant in the file CollectionNames.java.
		return CollectionNames.REFERENCING;
	}

	@Override
	protected Class<Referencing> getEntityClass() {
		return Referencing.class;
	}

	@Override
	protected Bson getSetOnUpdate(Referencing entity) {
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
