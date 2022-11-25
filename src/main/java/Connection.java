import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.Arrays;


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

	public static Document findFirst(String collectionName) {
		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			return database.getCollection(collectionName).find().first();
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

		System.out.println(Connection.findFirst(collectionName).toJson());

		Partner partner = new Partner("Test", "https://test.com", "test");

		Connection.insert(collectionName, partner.toDocument());
	}
}
