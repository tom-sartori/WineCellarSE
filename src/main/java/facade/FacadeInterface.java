package facade;

import exception.InvalidUsernameException;
import logic.controller.referencing.ReferencingController;
import org.bson.types.ObjectId;
import persistence.entity.partner.Partner;
import persistence.entity.referencing.Referencing;
import persistence.entity.user.User;

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
	 * Register a user.
	 *
	 * @param user The user to register.
	 * @return The id of the inserted user.
	 * @throws InvalidUsernameException if the username is already taken.
	 */
	ObjectId register(User user) throws InvalidUsernameException;

	/** Login a user.
	 *
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return The user if the login is successful.
	 */
	User login(String username, String password);

	User getOneUser(ObjectId id);

	User getOneUserByUsername(String username);

	boolean updateOneUser(ObjectId id, User user);

	boolean deleteOneUser(ObjectId id);

	/**
	 * Insert a referencing.
	 *
	 * @param referencing The referencing to insert.
	 * @return The id of the inserted referencing.
	 */
	ObjectId insertOneReferencing(Referencing referencing);

	/**
	 * Get all referencings.
	 *
	 * @return A list of referencings.
	 */
	List<Referencing> getReferencingList();

	/**
	 * Get a referencing by its id.
	 *
	 * @param id The id of the referencing.
	 * @return The referencing or null if not found.
	 */
	Referencing getOneReferencing(ObjectId id);

	/**
	 * Get referencings by their importanceLevel.
	 *
	 * @param importanceLevel The level of importance of the searched referencings.
	 * @return A list of referencings.
	 */
	List<Referencing> getReferencingByLevel(int importanceLevel);

	/**
	 * Get referencings by their company id.
	 *
	 * @param company The id of the referenced company.
	 * @return A list of referencings.
	 */
	List<Referencing> getReferencingByCompany(ObjectId company);
	/**
	 * Update a referencing.
	 *
	 * @param id The id of the referencing to update.
	 * @param referencing The new referencing.
	 * @return true if the referencing has been updated, false otherwise.
	 */
	boolean updateOneReferencing(ObjectId id, Referencing referencing);

	/**
	 * Delete a referencing.
	 *
	 * @param id The id of the referencing to delete.
	 * @return true if the referencing has been deleted, false otherwise.
	 */
	boolean deleteOneReferencing(ObjectId id);

	/**
	 * Update the status of a referencing.
	 *
	 * @param id The id of the referencing to update.
	 * @param referencing The new referencing.
	 * @return true if the referencing has been updated, false otherwise.
	 */
	boolean updateStatus(ObjectId id, Referencing referencing);
}
