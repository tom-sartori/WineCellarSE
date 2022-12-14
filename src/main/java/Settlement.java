import facade.Facade;
import facade.FacadeInterface;
import persistence.entity.partner.Partner;
import persistence.entity.partner.PartnerType;

public class Settlement {

	private static FacadeInterface facade = Facade.getInstance();

	public static void main(String[] args) {
		partnerSettlement();
	}

	private static void partnerSettlement() {
		facade.insertOnePartner(new Partner("Le Bistrot du Vin", PartnerType.RESTAURANT, "https://www.lebistrotduvin.fr/", "1 rue de la République, 75003 Paris"));
		facade.insertOnePartner(new Partner("Kubavin", PartnerType.SHOP, "https://www.kubavin.com/", "1 rue du vin, 75003 Paris"));
		facade.insertOnePartner(new Partner("Le Vin à la Maison", PartnerType.WINE_SHOP, "https://www.levinalamaison.fr/", "1 rue du vin, 75003 Paris"));
		facade.insertOnePartner(new Partner("Vin et Cie", PartnerType.OTHER, "https://www.vinetcie.fr/", "1 rue du vin, 75003 Paris"));
		facade.insertOnePartner(new Partner("Le Vin et Moi", PartnerType.WINE_SHOP, "https://www.levinetmoi.fr/", "1 rue du vin, 75003 Paris"));
	}
}
