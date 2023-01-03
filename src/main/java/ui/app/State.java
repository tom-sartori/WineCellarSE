package ui.app;

import persistence.entity.guide.Guide;
import persistence.entity.user.User;

public class State {
	private static State instance;
	private User currentUser;
	private Guide currentGuide;

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

	public void setCurrentUser(User currentUser) {
		System.out.println("The current user is now: " + currentUser.getUsername() + ". ");
		this.currentUser = currentUser;
	}

	public Guide getCurrentGuide() { return currentGuide;}

	public void setCurrentGuide(Guide currentGuide) {
		this.currentGuide = currentGuide;
	}
}
