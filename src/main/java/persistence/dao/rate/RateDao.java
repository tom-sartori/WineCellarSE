package persistence.dao.rate;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import exception.NotFoundException;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.AbstractDao;
import persistence.entity.rate.Rate;

import java.util.List;

/**
 * RateDao class extending Dao class parametrized with Event class.
 */

public class RateDao extends AbstractDao<Rate> {

	/**
	 * Instance of RateDao to ensure Singleton design pattern.
	 */
	private static RateDao instance;

	/**
	 * Private constructor for RateDao to ensure Singleton design pattern.
	 */
	private RateDao() { }

	/**
	 * @return the instance of RateDao to ensure Singleton design pattern.
	 */
	public static RateDao getInstance() {
		if (instance == null) {
			instance = new RateDao();
		}
		return instance;
	}

	/**
	 * @return the constant name of the specific Collection (Rate).
	 */
	@Override
	protected String getCollectionName() {
		return CollectionNames.RATE;
	}

	/**
	 * @return the class of the specific Entity (Rate).
	 */
	@Override
	protected Class<Rate> getEntityClass() {
		return Rate.class;
	}

	@Override
	protected Bson getSetOnUpdate(Rate entity) {
		return Updates.combine(
				Updates.set("rate", entity.getRate()),
				Updates.set("comment", entity.getComment()),
				Updates.set("lastModified", entity.getLastModified()),
				Updates.set("modified", entity.isModified()),
				Updates.set("ownerRef", entity.getOwnerRef())
		);

	}
//methodes annexes
	/**
	 * Get all the rates of a user.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the rates of the user if there are any, otherwise throws a NotFoundException.
	 */
	public List<Rate> getRateListFromUser(ObjectId userId) throws NotFoundException {
		BsonDocument filter = new BsonDocument();
		filter.append("ownerRef", new org.bson.BsonObjectId(userId));
		return findAllWithFilter(filter);
	}

	/**
	 * Get all the rates of a subject.
	 *
	 * @param subjectId The id of the subject.
	 *
	 * @return A list of all the rates of the subject if there are any, otherwise throws a NotFoundException.
	 */
	public List<Rate> getRateListFromSubject(ObjectId subjectId) throws NotFoundException {
		BsonDocument filter = new BsonDocument();
		filter.append("subjectRef", new org.bson.BsonObjectId(subjectId));
		return findAllWithFilter(filter);
	}
}
