package persistence.dao.sample;

import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.AbstractDao;
import persistence.entity.sample.Sample;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class SampleDao extends AbstractDao<Sample> {

	private static SampleDao instance;

	private SampleDao() { }

	public static SampleDao getInstance() {
		if (instance == null) {
			instance = new SampleDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		/// TODO : Add this constant in the file CollectionNames.java.
		return CollectionNames.SAMPLE;
	}

	@Override
	protected Class<Sample> getEntityClass() {
		return Sample.class;
	}

	@Override
	protected Bson getSetOnUpdate(Sample entity) {
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
