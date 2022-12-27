package ui.app;

import persistence.entity.cellar.Cellar;
import persistence.entity.user.User;

public class State {
	private static State instance;
	private User currentUser;

	private Cellar selectedCellar;

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

	public Cellar getSelectedCellar() {
		return selectedCellar;
	}

	public void setSelectedCellar(Cellar selectedCellar) {
		System.out.println("The selected cellar is now: " + selectedCellar.getName() + ". ");
		this.selectedCellar = selectedCellar;
	}
}
