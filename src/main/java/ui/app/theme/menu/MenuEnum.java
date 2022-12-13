package ui.app.theme.menu;

public enum MenuEnum {
	USER("Utilisateur", "UserPage"),
	PARTNER("Partenaires", "PartnerPage");

	private final String navName;
	private final String pageName;

	MenuEnum(String navName, String pageName) {
		this.navName = navName;
		this.pageName = pageName;
	}

	public String getNavName() {
		return navName;
	}

	public String getPageName() {
		return pageName;
	}
}
