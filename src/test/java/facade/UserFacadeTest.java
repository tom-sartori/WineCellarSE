package facade;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.user.MustBeAnAdminException;
import exception.user.NoLoggedUser;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

	private final FacadeInterface facade = Facade.getInstance();

	@Test
	void registerTest() {
		String username = "michel333";
		String password = "michel";
		User user = new User(username, password);
//		user.setAdmin(true);
		ObjectId userId = facade.register(user);

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
	void friendTest() throws NoLoggedUser {
		String username = "michel";
		String password = "michel";

		String friendUsername = "jeanjean";


		User loggedUser = Facade.getInstance().login(username, password);


		// Request
		Facade.getInstance().addFriend(friendUsername);

		assertTrue(loggedUser.getFriends().stream().anyMatch(friend -> friend.getUsername().equals(friendUsername)));
		assertFalse(loggedUser.getFriends().stream().filter(friend -> friend.getUsername().equals(friendUsername)).findFirst().get().isAccepted());

		// Check request in db.
		User loggedUserDb = Facade.getInstance().getOneUser(loggedUser.getId());
		assertTrue(loggedUserDb.getFriends().stream().anyMatch(friend -> friend.getUsername().equals(friendUsername)));
		assertFalse(loggedUserDb.getFriends().stream().filter(friend -> friend.getUsername().equals(friendUsername)).findFirst().get().isAccepted());

		User requestedFriend = Facade.getInstance().getOneUserByUsername(friendUsername);
		assertTrue(requestedFriend.getFriendRequests().stream().anyMatch(friend -> friend.getUsername().equals(username)));
	}

	@Test
	void deleteOneUser() {
		String username = "michelll";
		try {
			facade.deleteOneUser(username);
		} catch (MustBeAnAdminException e) {
			throw new RuntimeException(e);
		}
	}
}
