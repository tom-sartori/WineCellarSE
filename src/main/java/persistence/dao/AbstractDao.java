package persistence.dao;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import persistence.entity.Entity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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

	/**
	 * @return The name of the collection in the database.
	 */
	protected abstract String getCollectionName();

	/**
	 * @return The class of the generic type used.
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * Insert one entity of the parametrized type in the database.
	 *
	 * @param entity The entity to insert.
	 * @return The id of the inserted entity.
	 */
	@Override
	public ObjectId insertOne(T entity) {
		try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			InsertOneResult insertOneResult = collection.insertOne(entity);
			return Objects.requireNonNull(insertOneResult.getInsertedId()).asObjectId().getValue();
		}
		catch (Exception e) {
			e.printStackTrace();
			/// TODO: handle exception.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Find all entities of the parametrized type from the database.
	 *
	 * @return The list of all entities.
	 */
	@Override
	public ArrayList<T> findAll() {
		try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			return collection.find().into(new ArrayList<>());
		}
		catch (Exception e) {
			e.printStackTrace();
			/// TODO: handle exception.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Find one entity of the parametrized type from the database by its id.
	 *
	 * @param id The id of the entity to find.
	 * @return The entity found or an empty optional.
	 */
	@Override
	public Optional<T> findOne(ObjectId id) {
		try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			return Optional.ofNullable(collection.find(eq("_id", id)).first());
		}
		catch (Exception e) {
			e.printStackTrace();
			/// TODO: handle exception.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Update one entity of the parametrized type in the database.
	 *
	 * @param newEntity The entity to update.
	 * @return The number of updated entities.
	 */
	@Override
	public long updateOne(ObjectId id, T newEntity) {
		try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			UpdateResult updateResult = collection.updateOne(eq("_id", id), getSetOnUpdate(newEntity));
			return updateResult.getModifiedCount();
		}
		catch (Exception e) {
			e.printStackTrace();
			/// TODO: handle exception.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Delete one entity of the parametrized type from the database.
	 *
	 * @param id The id of the entity to delete.
	 * @return The number of deleted entities.
	 */
	@Override
	public long deleteOne(ObjectId id) {
		try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			DeleteResult deleteResult = collection.deleteOne(eq("_id", id));
			return deleteResult.getDeletedCount();
		}
		catch (Exception e) {
			e.printStackTrace();
			/// TODO: handle exception.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get MongoDb settings in order to map data with the entity class.
	 *
	 * @return The settings.
	 */
	protected MongoClientSettings getClientSettings() {
		ConnectionString connectionString = new ConnectionString(uri);
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		return MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.build();
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
