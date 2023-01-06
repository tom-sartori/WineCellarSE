package exception.user;

public class NoLoggedUser extends Exception {

	public NoLoggedUser() {
		super("No user is logged in.");
	}
}
