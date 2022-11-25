package connectiontodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import model.Partner;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class Connection {

	private static final String uri = "mongodb+srv://michel:michel@cluster0.54bwiq3.mongodb.net/?retryWrites=true&w=majority";
	private static final String databaseName = "winecellar-db";

	private Connection() { }

	public static FindIterable<Document> find(String collectionName) {
		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			return database.getCollection(collectionName).find();
		}
		catch (Exception e) {
			System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
			throw new RuntimeException();
		}	}

	public static Partner findFirst(String collectionName) {
		ConnectionString connectionString = new ConnectionString(uri);
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.build();
		try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Partner> partners = database.getCollection(collectionName, Partner.class);
			return partners.find().first();
		}
		catch (Exception e) {
			System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
			throw new RuntimeException();
		}
	}

	public static InsertOneResult insert(String collectionName, Document document) {
		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			return database.getCollection(collectionName).insertOne(document);
		}
		catch (Exception e) {
			System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
		String collectionName = "partners";

//		System.out.println(Connection.findFirst(collectionName).toJson());

		Partner partner = new Partner("Test", "https://test.com", "test");

		Connection.insert(collectionName, partner.toDocument());
	}
}
