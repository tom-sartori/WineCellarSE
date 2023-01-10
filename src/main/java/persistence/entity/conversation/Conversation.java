package persistence.entity.conversation;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Conversation implements Entity<Conversation> {
	private ObjectId id;
	private List<String> usernames;
	private List<Message> messages;
	private Date lastUpdate;

	public Conversation() { }

	public Conversation(List<String> usernames, List<Message> messages, Date lastUpdate) {
		this.usernames = usernames;
		this.messages = messages;
		this.lastUpdate = lastUpdate;
	}

	public Conversation(String username1, String username2) {
		List<String> usernames = new ArrayList<>();
		usernames.add(username1);
		usernames.add(username2);
		this.usernames = usernames;

		this.messages = new ArrayList<>();
		this.lastUpdate = new Date();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Conversation that = (Conversation) o;
		return Objects.equals(id, that.id) && Objects.equals(usernames, that.usernames) && Objects.equals(messages, that.messages) && Objects.equals(lastUpdate, that.lastUpdate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, usernames, messages, lastUpdate);
	}

	@Override
	public int compareTo(Conversation o) {
		return o.lastUpdate.compareTo(lastUpdate);
	}
}
