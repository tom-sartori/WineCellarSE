package ui.app.theme.menu.role;

import ui.app.page.company.referencing.ReferencingPage;
import ui.app.page.company.referencing.creation.ReferencingCreation;
import ui.app.page.company.referencing.list.ReferencingList;
import ui.app.page.company.CompanyPage;
import ui.app.page.company.advertising.creation.AdvertisingCreation;
import ui.app.page.company.advertising.list.AdvertisingList;
import ui.app.page.company.event.EventPage;
import ui.app.page.company.event.creation.EventCreation;
import ui.app.page.company.event.list.EventList;
import ui.app.page.partner.PartnerPage;
import ui.app.page.user.login.Login;
import ui.app.page.user.register.Register;

import java.util.Arrays;
import java.util.List;

public enum MenuEnumPublic implements MenuEnumInterface {
	USER("Utilisateur"),
	REGISTER("S'enregistrer", Register.class, USER),
	LOGIN("Se connecter", Login.class, USER),
	PARTNER("Partenaires", PartnerPage.class),
	COMPANY("Entreprise", CompanyPage.class),
	ADVERTISING("Publicités"),
	ADVERTISINGLIST("Toutes vos publicités", AdvertisingList.class, ADVERTISING),
	ADVERTISINGCREATION("Créer une publicité", AdvertisingCreation.class, ADVERTISING);

	private final String navigationTitle;
	private final Class<?> controllerClass;
	private final MenuEnumPublic parent;

	/**
	 * Construct a parent menu which has no default navigation page. If you click on the menu, nothing will happen.
	 *
	 * @param navigationTitle The title of the menu shown in the menu bar.
	 */
	MenuEnumPublic(String navigationTitle) {
		this.navigationTitle = navigationTitle;
		this.controllerClass = null;
		this.parent = null;
	}

	/**
	 * Construct a menu with a default navigation page.
	 *
	 * @param navigationTitle The title of the menu shown in the menu bar.
	 * @param controllerClass The controller class of the default navigation page.
	 */
	MenuEnumPublic(String navigationTitle, Class<?> controllerClass) {
		this.navigationTitle = navigationTitle;
		this.controllerClass = controllerClass;
		this.parent = null;
	}

	/**
	 * Construct a menu with a default navigation page and a parent menu.
	 *
	 * @param navigationTitle The title of the menu shown in the menu bar.
	 * @param controllerClass The controller class of the default navigation page.
	 * @param parent The parent menu.
	 */
	MenuEnumPublic(String navigationTitle, Class<?> controllerClass, MenuEnumPublic parent) {
		this.navigationTitle = navigationTitle;
		this.controllerClass = controllerClass;
		this.parent = parent;
	}

	public String getNavigationTitle() {
		return navigationTitle;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public MenuEnumPublic getParent() {
		return parent;
	}

	public List<MenuEnumPublic> getSubMenus() {
		return Arrays.stream(MenuEnumPublic.values())
				.filter(menu -> menu.getParent() == this)
				.toList();
	}

	public boolean isParentMenu() {
		return parent == null;
	}
}
