package ui.app;

import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import persistence.entity.user.User;

public class State {
	private static State instance;
	private User currentUser;

	private Cellar selectedCellar;

	private Wall selectedWall;

	private EmplacementBottle selectedEmplacementBottle;

	private Bottle selectedBottle;

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
		this.selectedCellar = selectedCellar;
	}

	public Wall getSelectedWall() {
		return selectedWall;
	}

	public void setSelectedWall(Wall selectedWall) {
		this.selectedWall = selectedWall;
	}

	public EmplacementBottle getSelectedEmplacementBottle() {
		return selectedEmplacementBottle;
	}

	public void setSelectedEmplacementBottle(EmplacementBottle selectedEmplacementBottle) {
		this.selectedEmplacementBottle = selectedEmplacementBottle;
	}

	public Bottle getSelectedBottle() {
		return selectedBottle;
	}

	public void setSelectedBottle(Bottle selectedBottle) {
		this.selectedBottle = selectedBottle;
	}
}
