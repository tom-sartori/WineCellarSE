package ui.app.theme.menu.role;

import ui.app.page.cellar.CellarPage;
import ui.app.page.cellar.lists.cellarbyuser.CellarByUser;
import ui.app.page.cellar.lists.friendscellar.FriendCellarList;
import ui.app.page.cellar.lists.publiccellars.PublicCellars;
import ui.app.page.cellar.lists.shared.SharedWithMeCellars;
import ui.app.page.company.admin.CompanyAdmin;
import ui.app.page.company.advertising.list.AdvertisingList;
import ui.app.page.company.event.list.EventList;
import ui.app.page.company.list.CompanyList;
import ui.app.page.company.list.user.CompanyListByUser;
import ui.app.page.conversation.ConversationPage;
import ui.app.page.guides.guideCreation.GuideCreation;
import ui.app.page.guides.list.GuideList;
import ui.app.page.notification.list.NotificationList;
import ui.app.page.partner.detail.PartnerDetail;
import ui.app.page.partner.list.PartnerList;
import ui.app.page.user.friend.FriendList;
import ui.app.page.user.logout.Logout;
import ui.app.page.user.profile.Profile;

import java.util.Arrays;
import java.util.List;

public enum MenuEnumAdmin implements MenuEnumInterface {
	CELLAR("Caves",CellarPage.class),
	CELLAR_BY_USER("Mes caves", CellarByUser.class, CELLAR),
	CELLAR_FROM_FRIENDS("Les caves de mes amis", FriendCellarList.class, CELLAR),
	CELLAR_SHARED_WITH_ME("Mes caves partagées", SharedWithMeCellars.class, CELLAR),
	PUBLIC_CELLARS("Caves publiques", PublicCellars.class, CELLAR),
	COMPANY("Entreprise", CompanyList.class),
	COMPANY_BY_USER("Mes entreprises", CompanyListByUser.class, COMPANY),
	COMPANY_LIST("Toutes les entreprises", CompanyList.class, COMPANY),
	COMPANY_ADMIN_LIST("Toutes les demandes d'entreprises ", CompanyAdmin.class, COMPANY),
	EVENT_LIST("Tous les évènements", EventList.class, COMPANY),
	ADVERTISING_REQUESTS_LIST("Toutes les demandes de publicité", AdvertisingList.class, COMPANY),
	PARTNER("Partenaires", PartnerList.class),
	PARTNER_LIST("Liste des partenaires", PartnerList.class, PARTNER),
	CREATE_PARTNER("Création", PartnerDetail.class, PARTNER),
	GUIDE("Guides", GuideList.class),
	GUIDE_ALL("Les guides", GuideList.class, GUIDE),
	GUIDE_CREATION("Création d'un guide", GuideCreation.class, GUIDE),
	USER("Utilisateur", Profile.class),
	CONVERSATION("Mes messages", ConversationPage.class, USER),
	PROFILE("Mon profil", Profile.class, USER),
	FRIEND("Mes amis", FriendList.class, USER),
	NOTIFICATION("Mes notifications", NotificationList.class, USER),
	LOGOUT("Déconnexion", Logout.class, USER);


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
