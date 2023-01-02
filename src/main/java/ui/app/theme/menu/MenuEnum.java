package ui.app.theme.menu;

import ui.app.page.partner.PartnerPage;
import ui.app.page.user.login.Login;
import ui.app.page.user.register.Register;

import java.util.Arrays;
import java.util.List;

public enum MenuEnum {
	USER("Utilisateur"),
	REGISTER("S'enregistrer", Register.class, USER),
	LOGIN("Login", Login.class, USER),
	PARTNER("Partenaires", PartnerPage.class);

	private final String navigationTitle;
	private final Class<?> controllerClass;
	private final MenuEnum parent;

	/**
	 * Construct a parent menu which has no default navigation page. If you click on the menu, nothing will happen.
	 *
	 * @param navigationTitle The title of the menu shown in the menu bar.
	 */
	MenuEnum(String navigationTitle) {
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
	MenuEnum(String navigationTitle, Class<?> controllerClass) {
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
	MenuEnum(String navigationTitle, Class<?> controllerClass, MenuEnum parent) {
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

	public MenuEnum getParent() {
		return parent;
	}

	public List<MenuEnum> getSubMenus() {
		return Arrays.stream(MenuEnum.values())
				.filter(menu -> menu.getParent() == this)
				.toList();
	}

	public boolean isParentMenu() {
		return parent == null;
	}
}
