package logic.controller.conversation;

import exception.user.NoLoggedUser;
import facade.Facade;
import logic.controller.AbstractController;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.conversation.ConversationMongoDao;
import persistence.entity.conversation.Conversation;
import persistence.entity.conversation.Message;
import persistence.entity.user.User;

import java.util.List;

/**
 * ConversationController class extending Controller class parametrized with Conversation class.
 */
public class ConversationController extends AbstractController<Conversation> {

    /**
     * Instance of ConversationController to ensure Singleton design pattern.
     */
    private static ConversationController instance;

    /**
     * Private constructor for ConversationController to ensure Singleton design pattern.
     */
    private ConversationController() { }

    /**
     * @return the instance of ConversationController to ensure Singleton design pattern.
     */
    public static ConversationController getInstance() {
        if(instance == null){
            instance = new ConversationController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (ConversationDao).
     */
    @Override
    protected ConversationMongoDao getDao() {
        return ConversationMongoDao.getInstance();
    }

    @Override
    public ObjectId insertOne(Conversation entity) {
        User loggedUser = null;
        try {
            loggedUser = Facade.getInstance().getLoggedUser();

            BsonDocument filter = new BsonDocument();
            filter.append("usernames", new org.bson.BsonString(loggedUser.getUsername()));

        } catch (NoLoggedUser ignore) { }

        return super.insertOne(entity);
    }

    /**
     * Return the conversations where the logged user is.
     *
     * @return a list of conversations.
     * @throws NoLoggedUser if there is no user logged.
     */
    public List<Conversation> getConversationList() throws NoLoggedUser {
        User loggedUser = Facade.getInstance().getLoggedUser();

        BsonDocument filter = new BsonDocument();
        filter.append("usernames", new org.bson.BsonString(loggedUser.getUsername()));

        return getDao().findAllWithFilter(filter);
    }

    /**
     * The current user send a message to the conversation in params.
     *
     * @param conversationId The id of the conversation.
     * @param message The message to send.
     */
    public void sendMessage(ObjectId conversationId, String message) {
        Conversation conversation = getDao().findOne(conversationId);
        try {
            Message message1 = new Message(Facade.getInstance().getLoggedUser().getUsername(), message);
            conversation.addMessage(message1);
            conversation.setLastUpdate(message1.getDate());
        }
        catch (NoLoggedUser ignore) { }

        getDao().updateOne(conversation.getId(), conversation);
    }
}
