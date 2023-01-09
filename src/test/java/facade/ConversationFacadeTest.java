package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import persistence.entity.conversation.Conversation;

import static org.junit.jupiter.api.Assertions.*;

class ConversationFacadeTest {

	private final FacadeInterface facade = Facade.getInstance();


	@Test
	void test() {
		String username = "michel";
		String password = "michel";

		String username2 = "jeanjean";

		Facade.getInstance().login(username, password);

		ObjectId conversationId = Facade.getInstance().insertOneConversation(new Conversation(username, username2));

		Facade.getInstance().sendMessage(conversationId, "Bonsoir");
	}
}
