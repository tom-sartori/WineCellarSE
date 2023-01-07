package persistence.dao.advertising;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.AbstractDao;
import persistence.entity.advertising.Advertising;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

/**
 * AdvertisingDao class extending Dao class parametrized with Advertising class.
 */

public class AdvertisingDao extends AbstractDao<Advertising> {

	/**
	 * Instance of AdvertisingDao to ensure Singleton design pattern.
	 */
	private static AdvertisingDao instance;

	/**
	 * Private constructor for AdvertisingDao to ensure Singleton design pattern.
	 */
	private AdvertisingDao() { }

	/**
	 * @return the instance of AdvertisingDao to ensure Singleton design pattern.
	 */
	public static AdvertisingDao getInstance() {
		if (instance == null) {
			instance = new AdvertisingDao();
		}
		return instance;
	}

	/**
	 * @return the constant name of the specific Collection (Advertising).
	 */
	@Override
	protected String getCollectionName() {
		return CollectionNames.ADVERTISING;
	}

	/**
	 * @return the class of the specific Entity (Advertising).
	 */
	@Override
	protected Class<Advertising> getEntityClass() {
		return Advertising.class;
	}

	@Override
	protected Bson getSetOnUpdate(Advertising entity) {
		List<Bson> updateList = new ArrayList<>();
		updateList.add(Updates.set("name", entity.getName()));
		updateList.add(Updates.set("description", entity.getDescription()));
		updateList.add(Updates.set("url", entity.getUrl()));
		updateList.add(Updates.set("startDate", entity.getStartDate()));
		updateList.add(Updates.set("endDate", entity.getEndDate()));
		updateList.add(Updates.set("active", entity.isActive()));
		updateList.add(Updates.set("payed", entity.isPayed()));
		updateList.add(Updates.set("nbViews", entity.getNbViews()));
		updateList.add(Updates.set("company", entity.getCompany()));

		if (entity.getLink() != null) {
			updateList.add(Updates.set("link", entity.getLink()));
		}

		return combine(updateList);
	}
}
