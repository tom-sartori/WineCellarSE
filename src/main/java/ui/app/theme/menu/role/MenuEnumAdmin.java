package ui.app.theme.menu.role;

import ui.app.page.cellar.CellarPage;
import ui.app.page.cellar.lists.cellarbyuser.CellarByUser;
import ui.app.page.cellar.lists.friendscellar.FriendCellarList;
import ui.app.page.cellar.lists.publiccellars.PublicCellars;
import ui.app.page.cellar.lists.shared.SharedWithMeCellars;
import ui.app.page.company.CompanyPage;
import ui.app.page.company.admin.CompanyAdmin;
import ui.app.page.company.advertising.creation.AdvertisingCreation;
import ui.app.page.company.advertising.list.AdvertisingList;
import ui.app.page.company.event.creation.EventCreation;
import ui.app.page.company.event.list.EventList;
import ui.app.page.company.list.CompanyList;
import ui.app.page.company.list.user.CompanyListByUser;
import ui.app.page.company.referencing.creation.ReferencingCreation;
import ui.app.page.company.referencing.list.ReferencingList;
import ui.app.page.guides.Guides;
import ui.app.page.guides.guideCreation.GuideCreation;
import ui.app.page.guides.list.GuideList;
import ui.app.page.notification.list.NotificationList;
import ui.app.page.conversation.ConversationPage;
import ui.app.page.partner.PartnerPage;
import ui.app.page.partner.detail.PartnerDetail;
import ui.app.page.partner.list.PartnerList;
import ui.app.page.user.friend.FriendList;
import ui.app.page.rates.RatePage;
import ui.app.page.user.login.Login;
import ui.app.page.user.logout.Logout;
import ui.app.page.user.profile.Profile;
import ui.app.page.user.register.Register;

import java.util.Arrays;
import java.util.List;

public enum MenuEnumAdmin implements MenuEnumInterface {
	USER("Utilisateur", Profile.class),
	REGISTER("S'enregistrer", Register.class, USER),
	LOGIN("Se connecter", Login.class, USER),
	LOGOUT("Déconnexion", Logout.class, USER),
	PROFILE("Mon profil", Profile.class, USER),
	FRIEND("Mes amis", FriendList.class, USER),
	PARTNER("Partenaires", PartnerList.class),
	CREATE_PARTNER("Création", PartnerDetail.class, PARTNER),
	COMPANY("Entreprise", CompanyPage.class),
	COMPANYLIST("Toutes les entreprises", CompanyList.class, COMPANY),
	COMPANYADMINLIST("Toutes les demandes d'entreprises ", CompanyAdmin.class, COMPANY),
	EVENTLIST("Tous les évènements", EventList.class, COMPANY),
	ADVERTISINGREQUESTSLIST("Toutes les demandes de publicité", AdvertisingList.class, COMPANY),
	CELLAR("Caves",CellarPage .class),
	CELLARBYUSER("Mes caves", CellarByUser.class, CELLAR),
	CELLARFROMFRIENDS("Les caves de mes amis", FriendCellarList.class, CELLAR),
	CELLARSHAREDWITHME("Mes caves partagées", SharedWithMeCellars.class, CELLAR),
	COMPANYBYUSER("Mes entreprises", CompanyListByUser.class, COMPANY),
	PUBLICCELLARS("Caves publiques",PublicCellars .class, CELLAR),
	GUIDE("Guides"),
	GUIDEALL("Les guides", GuideList.class, GUIDE),
	GUIDECREATION("Création d'un guide", GuideCreation.class, GUIDE),
	NOTIFICATION("Notifications",NotificationList .class),
	CONVERSATION("Messages", ConversationPage.class);

	private final String navigationTitle;
	private final Class<?> controllerClass;
	private final MenuEnumAdmin parent;

	/**
	 * Construct a parent menu which has no default navigation page. If you click on the menu, nothing will happen.
	 *
	 * @param navigationTitle The title of the menu shown in the menu bar.
	 */
	MenuEnumAdmin(String navigationTitle) {
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
	MenuEnumAdmin(String navigationTitle, Class<?> controllerClass) {
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
	MenuEnumAdmin(String navigationTitle, Class<?> controllerClass, MenuEnumAdmin parent) {
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

	public MenuEnumAdmin getParent() {
		return parent;
	}

	public List<MenuEnumAdmin> getSubMenus() {
		return Arrays.stream(MenuEnumAdmin.values())
				.filter(menu -> menu.getParent() == this)
				.toList();
	}

	public boolean isParentMenu() {
		return parent == null;
	}
}
