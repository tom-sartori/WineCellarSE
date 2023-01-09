package ui.app.theme.menu.role;

import ui.app.page.cellar.CellarPage;
import ui.app.page.cellar.lists.cellarbyuser.CellarByUser;
import ui.app.page.cellar.lists.publiccellars.PublicCellars;
import ui.app.page.cellar.lists.shared.SharedWithMeCellars;
import ui.app.page.company.CompanyPage;
import ui.app.page.company.advertising.creation.AdvertisingCreation;
import ui.app.page.company.advertising.list.AdvertisingList;
import ui.app.page.company.event.creation.EventCreation;
import ui.app.page.company.event.list.EventList;
import ui.app.page.company.referencing.ReferencingCard;
import ui.app.page.guides.Guides;
import ui.app.page.company.event.creation.EventCreation;
import ui.app.page.company.event.list.EventList;
import ui.app.page.company.referencing.creation.ReferencingCreation;
import ui.app.page.company.referencing.list.ReferencingList;
import ui.app.page.notification.NotificationPage;
import ui.app.page.notification.list.NotificationList;
import ui.app.page.partner.PartnerPage;
import ui.app.page.rates.RatePage;
import ui.app.page.user.logout.Logout;
import ui.app.page.user.profile.Profile;

import java.util.Arrays;
import java.util.List;

public enum MenuEnumUser implements MenuEnumInterface {
	USER("Utilisateur"),
	PROFILE("Mon profil", Profile.class, USER),
	LOGOUT("Déconnexion", Logout.class, USER),
	PARTNER("Partenaires", PartnerPage.class),
	COMPANY("Entreprise", CompanyPage.class),
	ADVERTISINGLIST("Toutes vos publicités", AdvertisingList.class, COMPANY),
	ADVERTISINGCREATION("Créer une publicité", AdvertisingCreation.class, COMPANY),
	REFERNCINGLIST("Tous vos référencents", ReferencingList.class, COMPANY),
	REFERENCINGCREATION("Créer un référencement", ReferencingCreation.class, COMPANY),
	EVENTLIST("Tous vos évènements", EventList.class, COMPANY),
	EVENTCREATION("Créer un évènement", EventCreation.class, COMPANY),
	CELLAR("Caves",CellarPage .class),
	CELLARBYUSER("Mes caves", CellarByUser.class, CELLAR),
	CELLARSHAREDWITHME("Mes caves partagées", SharedWithMeCellars.class, CELLAR),
	PUBLICCELLARS("Caves publiques",PublicCellars .class, CELLAR),
	GUIDE("Guides", Guides.class),
	RATE("Notes", RatePage.class),
	NOTIFICATION("Notifications", NotificationPage.class);

	private final String navigationTitle;
	private final Class<?> controllerClass;
	private final MenuEnumUser parent;

	/**
	 * Construct a parent menu which has no default navigation page. If you click on the menu, nothing will happen.
	 *
	 * @param navigationTitle The title of the menu shown in the menu bar.
	 */
	MenuEnumUser(String navigationTitle) {
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
	MenuEnumUser(String navigationTitle, Class<?> controllerClass) {
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
	MenuEnumUser(String navigationTitle, Class<?> controllerClass, MenuEnumUser parent) {
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

	public MenuEnumUser getParent() {
		return parent;
	}

	public List<MenuEnumUser> getSubMenus() {
		return Arrays.stream(MenuEnumUser.values())
				.filter(menu -> menu.getParent() == this)
				.toList();
	}

	public boolean isParentMenu() {
		return parent == null;
	}
}
