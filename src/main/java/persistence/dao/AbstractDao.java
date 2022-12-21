package persistence.dao;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import exception.NotFoundException;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.connector.MongoConnector;
import persistence.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

/**
 * DAO abstract class which implements the DAO interface with generic methods.
 * The methods can be overridden in the concrete DAO classes.
 * This DAO uses mongoDB as a database.
 *
 * @param <T> The entity class which extends the Entity interface.
 */
public abstract class AbstractDao<T extends Entity<T>> implements Dao<T> {

	protected static final String uri = "mongodb+srv://michel:michel@cluster0.54bwiq3.mongodb.net/?retryWrites=true&w=majority";
	protected static final String databaseName = "winecellar-db";
	protected static final MongoConnector mongoConnector = MongoConnector.getInstance();

	/**
	 * @return The name of the collection in the database.
	 */
	protected abstract String getCollectionName();

	/**
	 * @return The class of the generic type used.
	 */
	protected abstract Class<T> getEntityClass();

	// TODO COMMENT
	protected MongoCollection<T> getCollection(){
		return mongoConnector.getDatabaseByName(databaseName).getCollection(getCollectionName(), getEntityClass());
	}

	/**
	 * Insert one entity of the parametrized type in the database.
	 *
	 * @param entity The entity to insert.
	 * @return The id of the inserted entity.
	 */
	@Override
	public ObjectId insertOne(T entity) throws MongoWriteException {
		InsertOneResult insertOneResult = getCollection().insertOne(entity);
		return Objects.requireNonNull(insertOneResult.getInsertedId()).asObjectId().getValue();
	}

	/**
	 * Find all entities of the parametrized type in the database that match the filter given in parameter.
	 *
	 * @param filter The filter to apply.
	 *               Example :
	 *                  BsonDocument filter = new BsonDocument();
	 * 					filter.append("username", new org.bson.BsonString(username));
	 *
	 * @return A list of entities.
	 *
	 * @throws Exception If no entity is returned.
	 */
	public List<T> findAllWithFilter(BsonDocument filter) throws Exception {
		ArrayList<T> response = getCollection().find(filter).into(new ArrayList<>());
		if (response.isEmpty()) {
			// TODO replace with Tom's custom exception.
			throw new Exception("not found");
		}
		return response;
	}

	/**
	 * Find all entities of the parametrized type from the database.
	 *
	 * @return The list of all entities.
	 */
	@Override
	public ArrayList<T> findAll() {
		return getCollection().find().into(new ArrayList<>());
	}

	/**
	 * Find the first entity which correspond to the filter in parameter.
	 *
	 * @param filter The mongo filter to find the entity.
	 * @return The entity found.
	 * @throws NotFoundException If no entity is found.
	 */
	protected T findOne(BsonDocument filter) throws NotFoundException {
		T entity = getCollection().find(filter).first();

		if (entity == null) {
			throw new NotFoundException("No entity found with the filter: " + filter);
		}
		else {
			return entity;
		}

	}

	/**
	 * Find one entity of the parametrized type from the database by its id.
	 *
	 * @param id The id of the entity to find.
	 * @return The entity found or null;
	 */
	@Override
	public T findOne(ObjectId id) {
		return findOne(eq("_id", id).toBsonDocument());
	}

	/**
	 * Update one entity of the parametrized type in the database.
	 *
	 * @param updateField fields to update ex: Updates.set("name", "Jean").
	 * @return true if the entity has been updated, false otherwise.
	 */
	protected boolean updateOne(ObjectId id, Bson updateField) {
		UpdateResult updateResult = getCollection().updateOne(eq("_id", id), updateField);
		return updateResult.getModifiedCount() == 1;
	}


	/**
	 * Update one entity of the parametrized type in the database.
	 *
	 * @param newEntity The entity to update.
	 * @return true if the entity has been updated, false otherwise.
	 */
	@Override
	public boolean updateOne(ObjectId id, T newEntity) {
		return updateOne(id, getSetOnUpdate(newEntity));
	}

	/**
	 * Delete one entity of the parametrized type from the database.
	 *
	 * @param id The id of the entity to delete.
	 * @return true if the entity has been deleted, false otherwise.
	 */
	@Override
	public boolean deleteOne(ObjectId id) {
		DeleteResult deleteResult = getCollection().deleteOne(eq("_id", id));
		return deleteResult.getDeletedCount() == 1;
	}

	/**
	 * Function used in updateOne() to set the fields to update.
	 * Must be implemented in the child class.
	 *
	 * @return the Bson object to use in updateOne(). Must be something like this :
	 *  	Updates.combine(
	 *      	Updates.set("runtime", 99),
	 *       	Updates.addToSet("genres", "Sports"),
	 *      	Updates.currentTimestamp("lastUpdated"));
	 */
	abstract protected Bson getSetOnUpdate(T entity);
}
