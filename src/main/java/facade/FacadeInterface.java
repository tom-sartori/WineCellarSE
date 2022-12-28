package facade;

import org.bson.types.ObjectId;
import persistence.entity.guide.Guide;
import persistence.entity.partner.Partner;

import java.util.List;

/**
 * Global facade interface.
 */
public interface FacadeInterface {

	/**
	 * Insert a partner.
	 *
	 * @param partner The partner to insert.
	 * @return The id of the inserted partner.
	 */
	ObjectId insertOnePartner(Partner partner);

	/**
	 * Get all partners.
	 *
	 * @return A list of partners.
	 */
	List<Partner> getPartnerList();

	/**
	 * Get a partner by its id.
	 *
	 * @param id The id of the partner.
	 * @return The partner.
	 */
	Partner getOnePartner(ObjectId id);

	/**
	 * Update a partner.
	 *
	 * @param id      The id of the partner to update.
	 * @param partner The partner to update.
	 * @return The number of updated partners.
	 */
	boolean updateOnePartner(ObjectId id, Partner partner);

	/**
	 * Delete a partner.
	 *
	 * @param id The id of the partner to delete.
	 * @return The number of deleted partners.
	 */
	boolean deleteOnePartner(ObjectId id);

	/**
	 * Insert a guide.
	 *
	 * @param guide The guide to insert.
	 * @return The id of the inserted guide.
	 */
	ObjectId insertOneGuide(Guide guide);

	/**
	 * Get all guides.
	 *
	 * @return A list of guides.
	 */
	List<Guide> getGuideList();

	/**
	 * Get a guide by its id.
	 *
	 * @param id The id of the guide.
	 * @return The guide.
	 */
	Guide getOneGuide(ObjectId id);

	/**
	 * Update a guide.
	 *
	 * @param id      The id of the guide to update.
	 * @param guide The guide to update.
	 * @return The number of updated guides.
	 */
	boolean updateOneGuide(ObjectId id, Guide guide);

	/**
	 * Delete a guide.
	 *
	 * @param id The id of the guide to delete.
	 * @return The number of deleted guides.
	 */
	boolean deleteOneGuide(ObjectId id);
}
