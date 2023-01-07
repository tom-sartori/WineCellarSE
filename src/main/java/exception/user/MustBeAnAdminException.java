package exception.user;

public class MustBeAnAdminException extends Exception {

	public MustBeAnAdminException() {
		super("You must be an admin to perform this action.");
	}
}
