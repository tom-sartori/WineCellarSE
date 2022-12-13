package facade;

import exception.InvalidUsernameException;
import org.bson.types.ObjectId;
import persistence.entity.partner.Partner;
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
}
