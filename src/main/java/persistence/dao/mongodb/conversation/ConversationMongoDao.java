package persistence.dao.mongodb.conversation;

import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.conversation.Conversation;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Updates.combine;

public class ConversationMongoDao extends AbstractMongoDao<Conversation> {

	private static ConversationMongoDao instance;

	private ConversationMongoDao() { }

	public static ConversationMongoDao getInstance() {
		if (instance == null) {
			instance = new ConversationMongoDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.CONVERSATION;
	}

	@Override
	protected Class<Conversation> getEntityClass() {
		return Conversation.class;
	}

	@Override
	protected Bson getSetOnUpdate(Conversation entity) {
		List<Bson> updateList = new ArrayList<>();

		updateList.add(Updates.set("usernames", entity.getUsernames()));
		updateList.add(Updates.set("messages", entity.getMessages()));
		updateList.add(Updates.set("lastUpdate", entity.getLastUpdate()));

		return combine(updateList);
	}

	@Override
	public ObjectId insertOne(Conversation entity) throws MongoWriteException {
		ArrayList<Conversation> conversations = getCollection().find(all("usernames", entity.getUsernames())).into(new ArrayList<>());

		if (conversations.size() == 0) {
			return super.insertOne(entity);
		}
		else {
			return conversations.get(0).getId();
		}
	}
}
