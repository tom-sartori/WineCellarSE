package persistence.entity.user;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Friend {

	private ObjectId id;
	private String username;
	private boolean accepted;

	public Friend() { }

	public Friend(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.accepted = false;
	}

	public Friend(User user, boolean accepted) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.accepted = accepted;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Friend friend = (Friend) o;
		return accepted == friend.accepted && Objects.equals(id, friend.id) && Objects.equals(username, friend.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, accepted);
	}
}
