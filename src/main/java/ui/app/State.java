package ui.app;

import persistence.entity.advertising.Advertising;
import persistence.entity.user.User;

import java.sql.SQLOutput;

public class State {
	private static State instance;
	private User currentUser;

	private String previousPage;

	private Advertising currentAdvertising;

	private State() { }

	public static State getInstance() {
		if (instance == null) {
			instance = new State();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Advertising getCurrentAdvertising() {
		return currentAdvertising;
	}

	public void setCurrentUser(User currentUser) {
		System.out.println("The current user is now: " + currentUser.getUsername() + ". ");
		this.currentUser = currentUser;
	}

	public void setCurrentAdvertising(Advertising advertising) {
		this.currentAdvertising = advertising;
		System.out.println("The current advertising is now: " + currentAdvertising.getName() + ". ");
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}
}
