package persistence.entity.user;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Objects;


public class User implements Entity<User> {

	private ObjectId id;
	private String username;
	private String password;

	public User() { }

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void handleOnCreate() {
		this.id = null;
	}

	@Override
	public void handleOnUpdate() {
		this.id = null;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password);
	}

	@Override
	public int compareTo(User o) {
		return username.compareTo(o.username);
	}
}
