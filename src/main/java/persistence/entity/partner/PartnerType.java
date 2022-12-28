package persistence.entity.partner;

public enum PartnerType {
	RESTAURANT("Restaurant"),
	WINE_SHOP("Caviste"),
	SHOP("Boutique"),
	OTHER("Autre");

	private final String name;

	PartnerType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
