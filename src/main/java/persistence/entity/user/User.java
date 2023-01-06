package persistence.entity.user;

import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Objects;


public class User implements Entity<User> {

	private ObjectId id;
	private String username;
	private String password;
	private boolean admin;
	@Nullable
	private String firstname;
	@Nullable
	private String lastname;
	@Nullable
	private String email;

	public User() { }

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.admin = false;
	}

	public User(String username, String password, @Nullable String firstname, @Nullable String lastname, @Nullable String email) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.admin = false;
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Nullable
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(@Nullable String firstname) {
		this.firstname = firstname;
	}

	@Nullable
	public String getLastname() {
		return lastname;
	}

	public void setLastname(@Nullable String lastname) {
		this.lastname = lastname;
	}

	@Nullable
	public String getEmail() {
		return email;
	}

	public void setEmail(@Nullable String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return admin == user.admin && Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, admin, firstname, lastname, email);
	}

	@Override
	public int compareTo(User o) {
		return username.compareTo(o.username);
	}
}
