package ui.app;

import persistence.entity.advertising.Advertising;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import persistence.entity.company.Company;
import persistence.entity.event.Event;
import persistence.entity.guide.Guide;
import persistence.entity.referencing.Referencing;
import persistence.entity.user.User;

public class State {
	private static State instance;
	private User currentUser;
	private Guide currentGuide;

	private String previousPage;

	private Advertising currentAdvertising;

	private Referencing currentReferencing;

	private Event currentEvent;

	private Cellar selectedCellar;

	private Wall selectedWall;

	private EmplacementBottle selectedEmplacementBottle;

	private Bottle selectedBottle;

	private Company selectedCompany;

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

	public Guide getCurrentGuide() { return currentGuide;}

	public void setCurrentGuide(Guide currentGuide) {
		this.currentGuide = currentGuide;
	}

	public void setCurrentAdvertising(Advertising advertising) {
		this.currentAdvertising = advertising;
		System.out.println("The current advertising is now: " + currentAdvertising.getName() + ". ");
	}

	public Referencing getCurrentReferencing() {
		return currentReferencing;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

	public void setCurrentReferencing(Referencing currentReferencing) {
		this.currentReferencing = currentReferencing;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
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

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}
}
