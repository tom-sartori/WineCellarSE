package model;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.Entity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class Model <T extends Entity<T>> {

	private static final String uri = "mongodb+srv://michel:michel@cluster0.54bwiq3.mongodb.net/?retryWrites=true&w=majority";
	private static final String databaseName = "winecellar-db";

	public T findFirst() {
		ConnectionString connectionString = new ConnectionString(uri);
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.build();

		try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<T> collection = database.getCollection(getCollectionName(), getEntityClass());
			return collection.find().first();
		}
		catch (Exception e) {
			/// TODO : Exception mapper.
			System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
			throw new RuntimeException();
		}
	}

	protected abstract String getCollectionName();
	protected abstract Class<T> getEntityClass();
}
