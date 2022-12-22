package facade;

import logic.controller.advertising.AdvertisingController;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import persistence.entity.partner.Partner;

import java.util.Date;
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
	 * Insert a advertising.
	 *
	 * @param advertising The advertising to insert.
	 * @return The id of the inserted advertising.
	 */
	ObjectId insertOneAdvertising(Advertising advertising);

	/**
	 * Get all advertisings.
	 *
	 * @return A list of advertisings.
	 */
	List<Advertising> getAdvertisingList();

	/**
	 * Get an advertising by its id.
	 *
	 * @param id The id of the advertising.
	 * @return The advertising or null if not found.
	 */
	Advertising getOneAdvertising(ObjectId id);

	/**
	 * Update a advertising.
	 *
	 * @param id The id of the advertising to update.
	 * @param advertising The new advertising.
	 * @return true if the advertising has been updated, false otherwise.
	 */
	boolean updateOneAdvertising(ObjectId id, Advertising advertising);

	/**
	 * Delete a advertising.
	 *
	 * @param id The id of the advertising to delete.
	 * @return true if the advertising has been deleted, false otherwise.
	 */
	boolean deleteOneAdvertising(ObjectId id);

	/**
	 * Renew an advertising.
	 *
	 * @param id The id of the advertising to renew.
	 * @param endDate The new end date of the advertising.
	 * @return true if the advertising has been renewed, false otherwise.
	 */
	boolean renewOneAdvertising(ObjectId id, Date endDate);

	/**
	 * Pay for an advertising.
	 *
	 * @param id The id of the advertising to pay for.
	 * @return true if the advertising has been paid, false otherwise.
	 */
	boolean payOneAdvertising(ObjectId id);

	/**
	 * add a view to an advertising.
	 *
	 * @param id The id of the advertising.
	 * @return true if the view was added to the advertising, false otherwise.
	 */
	boolean addView(ObjectId id);

	/**
	 * validate an advertising.
	 *
	 * @param id The id of the advertising to validate.
	 * @return true if the advertising has been validated, false otherwise.
	 */
	boolean validateAdvertising(ObjectId id);
}
