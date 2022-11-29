package facade;

import persistence.entity.partner.Partner;

import java.util.List;

/**
 * Global facade interface.
 */
public interface FacadeInterface {

	/**
	 * Get all partners.
	 *
	 * @return A list of partners.
	 */
	List<Partner> getPartnerList();
}
