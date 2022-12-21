package persistence.dao.advertizing;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.advertizing.Advertizing;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
/**
 * AdvertizingDao class extending Dao class parametrized with Advertizing class.
 */

public class AdvertizingDao extends AbstractDao<Advertizing> {

	/**
	 * Instance of AdvertizingDao to ensure Singleton design pattern.
	 */
	private static AdvertizingDao instance;

	/**
	 * Private constructor for AdvertizingDao to ensure Singleton design pattern.
	 */
	private AdvertizingDao() { }

	/**
	 * @return the instance of AdvertizingDao to ensure Singleton design pattern.
	 */
	public static AdvertizingDao getInstance() {
		if (instance == null) {
			instance = new AdvertizingDao();
		}
		return instance;
	}

	/**
	 * @return the constant name of the specific Collection (Advertizing).
	 */
	@Override
	protected String getCollectionName() {
		return CollectionNames.ADVERTIZING;
	}

	/**
	 * @return the class of the specific Entity (Advertizing).
	 */
	@Override
	protected Class<Advertizing> getEntityClass() {
		return Advertizing.class;
	}

	@Override
	protected Bson getSetOnUpdate(Advertizing entity) {
		List<Bson> updateList = new ArrayList<>();
		updateList.add(Updates.set("name", entity.getName()));
		updateList.add(Updates.set("description", entity.getDescription()));
		updateList.add(Updates.set("url", entity.getUrl()));
		updateList.add(Updates.set("startDate", entity.getStartDate()));
		updateList.add(Updates.set("endDate", entity.getEndDate()));
		updateList.add(Updates.set("isActive", entity.isActive()));
		updateList.add(Updates.set("payed", entity.isPayed()));
		updateList.add(Updates.set("nbViews", entity.getNbViews()));

		if (entity.getLink() != null) {
			updateList.add(Updates.set("link", entity.getLink()));
		}

		return combine(updateList);
	}
}
