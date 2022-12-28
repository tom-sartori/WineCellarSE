package facade;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import persistence.entity.user.User;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

	private final FacadeInterface facade = Facade.getInstance();

	@Test
	void registerTest() {
		String username = "michel";
		String password = "michel";
		ObjectId userId = facade.register(new User(username, password));

		assertNotNull(userId);
		assertThrows(InvalidUsernameException.class, () -> facade.register(new User(username, password)));
	}

	@Test
	void loginTest() {
		String username = "michel";
		String password = "michel";

		String wrongUsername = "michel2";
		String wrongPassword = "abc";

		User userRegistered;
		try {
			ObjectId idRegistered = facade.register(new User(username, password));
			userRegistered = facade.getOneUser(idRegistered);
		}
		catch (InvalidUsernameException e) {
			fail("Username of test already exists. ");
			return;
		}

		User userLoggedIn = facade.login(username, password);

		assertEquals(userRegistered, userLoggedIn);
		assertThrows(BadCredentialException.class, () -> facade.login(username, wrongPassword));
		assertThrows(BadCredentialException.class, () -> facade.login(wrongUsername, wrongPassword));
	}

	@Test
	void getUserList() {
	}

	@Test
	void getOneUser() {
	}

	@Test
	void updateOneUser() {
	}

	@Test
	void deleteOneUser() {
	}
}
