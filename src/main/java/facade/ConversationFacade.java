package facade;

import exception.user.NoLoggedUser;
import logic.controller.conversation.ConversationController;
import org.bson.types.ObjectId;
import persistence.entity.conversation.Conversation;

import java.util.List;

/**
 * Specific facade for Conversations.
 */
class ConversationFacade {

    /**
     * Singleton instance.
     */
    private static ConversationFacade instance;

    private ConversationFacade() { }

    /**
     * Get the singleton instance of the conversation facade.
     *
     * @return The singleton instance.
     */
    public static ConversationFacade getInstance() {
        if (instance == null) {
            instance = new ConversationFacade();
        }
        return instance;
    }

    /**
     * Insert a conversation.
     *
     * @param conversation The conversation to insert.
     * @return The id of the inserted conversation.
     */
    protected ObjectId insertOneConversation(Conversation conversation) {
        return ConversationController.getInstance().insertOne(conversation);
    }

    /**
     * Return the conversations where the logged user is.
     *
     * @return a list of conversations.
     * @throws NoLoggedUser if there is no user logged.
     */
    protected List<Conversation> getConversationList() throws NoLoggedUser {
        return ConversationController.getInstance().getConversationList();
    }

    /**
     * Get a conversation by its id.
     *
     * @param id The id of the conversation.
     * @return The conversation or null if not found.
     */
    protected Conversation getOneConversation(ObjectId id) {
        return ConversationController.getInstance().findOne(id);
    }

    /**
     * Update a conversation.
     *
     * @param id The id of the conversation to update.
     * @param conversation The new conversation.
     * @return true if the conversation has been updated, false otherwise.
     */
    protected boolean updateOneConversation(ObjectId id, Conversation conversation) {
        return ConversationController.getInstance().updateOne(id, conversation);
    }

    /**
     * Delete a conversation.
     *
     * @param id The id of the conversation to delete.
     * @return true if the conversation has been deleted, false otherwise.
     */
    protected boolean deleteOneConversation(ObjectId id) {
        return ConversationController.getInstance().deleteOne(id);
    }

    /**
     * The current user send a message to the conversation in params.
     *
     * @param conversationId The id of the conversation.
     * @param message The message to send.
     */
    protected void sendMessage(ObjectId conversationId, String message) {
        ConversationController.getInstance().sendMessage(conversationId, message);
    }

    /**
     * Find one conversation.
     *
     * @param id of the conversation to find.
     * @return the conversation.
     */
    protected Conversation findOneConversation(ObjectId id) {
        return ConversationController.getInstance().findOne(id);
    }
}
