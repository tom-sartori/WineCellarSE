package model;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import entity.Entity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class Model <T extends Entity<T>> {

	protected static final String uri = "mongodb+srv://michel:michel@cluster0.54bwiq3.mongodb.net/?retryWrites=true&w=majority";
	protected static final String databaseName = "winecellar-db";

	protected abstract String getCollectionName();

	protected abstract Class<T> getEntityClass();

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

	public T findOne(ObjectId id) {
		try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			return collection.find(eq("_id", id)).first();
		}
		catch (Exception e) {
			e.printStackTrace();
			/// TODO: handle exception.
			throw new RuntimeException(e);
		}
	}

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

	protected MongoClientSettings getClientSettings() {
		ConnectionString connectionString = new ConnectionString(uri);
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		return MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.build();
	}
}
