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
		return CollectionNames.ADVERTIZING;
	}

	@Override
	protected Class<Advertizing> getEntityClass() {
		return Advertizing.class;
	}

	@Override
	protected Bson getSetOnUpdate(Advertizing entity) {
		List<Bson> updateList = new ArrayList<>();

		updateList.add(Updates.set("name", entity.getName()));
		updateList.add(Updates.set("description", entity.getDescription()));
		updateList.add(Updates.set("image", entity.getImage()));
		updateList.add(Updates.set("startDate", entity.getStartDate()));
		updateList.add(Updates.set("endDate", entity.getEndDate()));
		updateList.add(Updates.set("nbViews", entity.getNbViews()));
		updateList.add(Updates.set("price", entity.getPrice()));
		updateList.add(Updates.set("isActive", entity.isActive()));

		/// TODO : Optionnels ?.
		if (entity.getLink() != null) {
			updateList.add(Updates.set("link", entity.getLink()));
		}

		return combine(updateList);
	}
}
