package persistence.entity.partner;

import java.util.Arrays;

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

	public static PartnerType getPartnerType(String name) {
		return Arrays.stream(PartnerType.values()).filter(x -> x.getName().equals(name)).findFirst().get();
	}
}
