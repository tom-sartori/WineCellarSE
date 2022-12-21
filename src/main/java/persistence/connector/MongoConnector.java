package persistence.connector;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import persistence.entity.cellar.Cellar;

import java.util.ArrayList;
import java.util.Objects;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnector {

    private static MongoConnector instance;
    private final MongoClient mongoClient;

    protected final String uri = "mongodb+srv://michel:michel@cluster0.54bwiq3.mongodb.net/?retryWrites=true&w=majority";

//    protected final String databaseName = "winecellar-db";

    private MongoConnector() {
        mongoClient = MongoClients.create(getClientSettings());
    }

    public static MongoConnector getInstance() {
        if (instance == null) {
            instance = new MongoConnector();
        }
        return instance;
    }

    // TODO TRY CATCH
    public MongoDatabase getDatabaseByName(String databaseName) {
        return this.mongoClient.getDatabase(databaseName);
    }

    private MongoClientSettings getClientSettings() {
        ConnectionString connectionString = new ConnectionString(uri);
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
    }
}
