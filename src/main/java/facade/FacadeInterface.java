package facade;

import exception.BadArgumentsException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import org.bson.types.ObjectId;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import persistence.entity.company.Company;
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
	 * Insert a cellar.
	 *
	 * @param cellar The cellar to insert.
	 * @return The id of the inserted cellar.
	 */
	ObjectId insertOneCellar(Cellar cellar);

	/**
	 * Get all Cellars.
	 *
	 * @return A list of cellars.
	 */
	List<Cellar> getCellarList();

	/**
	 * Get a cellar by its id.
	 *
	 * @param id The id of the cellar.
	 * @return The cellar or null if not found.
	 */
	Cellar getOneCellar(ObjectId id);

	/**
	 * Update a cellar.
	 *
	 * @param id The id of the cellar to update.
	 * @param cellar The new cellar.
	 *
	 * @return true if the cellar has been updated, false otherwise.
	 */
	boolean updateOneCellar(ObjectId id, Cellar cellar);

	/**
	 * Delete a cellar.
	 *
	 * @param id The id of the cellar to delete.
	 *
	 * @return true if the cellar has been deleted, false otherwise.
	 */
	boolean deleteOneCellar(ObjectId id);

	/**
	 * Add a cellar reader.
	 *
	 * @param user The user to add to readers.
	 * @param cellar The cellar to add the user to.
	 *
	 * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
	 */
	ObjectId addCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException;

	/**
	 * Remove a cellar reader.
	 *
	 * @param user The user to remove from readers.
	 * @param cellar The cellar to remove the user from.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId removeCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException;

	/**
	 * Add a cellar manager.
	 *
	 * @param user The user to add to managers.
	 * @param cellar The cellar to add the user to.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId addCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException;

	/**
	 * Remove a cellar manager.
	 *
	 * @param user The user to remove from managers.
	 * @param cellar The cellar to remove the user from.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId removeCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException;

	/**
	 * Add a wall to a cellar.
	 *
	 * @param cellar The cellar to add the wall to.
	 * @param wall The wall to add.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId addWall(Wall wall, ObjectId cellar) throws BadArgumentsException;

	/**
	 * Remove a wall from a cellar.
	 *
	 * @param cellar The cellar to remove the wall from.
	 * @param wall The wall to remove.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId removeWall(Wall wall, ObjectId cellar) throws BadArgumentsException;

	/**
	 * Add an emplacement to a wall.
	 *
	 * @param cellar The cellar to add the emplacement to.
	 * @param wall The wall to add the emplacement to.
	 * @param emplacementBottle The emplacement to add.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException;

	/**
	 * Remove an emplacement from a wall.
	 *
	 * @param cellar The cellar to remove the emplacement from.
	 * @param wall The wall to remove the emplacement from.
	 * @param emplacementBottle The emplacement to remove.
	 *
	 * @return The id of the updated cellar if the update was successful otherwise throws a BadArgumentsException.
	 */
	ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException;

	/**
	 * Increase the quantity of a bottle in a cellar.
	 *
	 * @param cellar The cellar to increase the quantity in.
	 *               The cellar must contain the wall.
	 * @param wall The wall to increase the quantity in.
	 *             the wall must be in the cellar and contain emplacementBottle.
	 * @param emplacementBottle The emplacement to increase the quantity in.
	 *                          The emplacement must be in the wall and contain the bottle.
	 * @param bottleQuantity The bottle to increase the quantity of.
	 *               The bottle must be in the emplacement.
	 *
	 * @return The id of the updated cellar if the bottle was found and updated otherwise throws a BadArgumentsException.
	 */
	ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException;

	/**
	 * Decrease the quantity of a bottle in a cellar if the quantity is greater than 0, else remove the bottle.
	 *
	 * @param cellar The cellar to increase the quantity in.
	 *               The cellar must contain the wall.
	 * @param wall The wall to increase the quantity in.
	 *             the wall must be in the cellar and contain emplacementBottle.
	 * @param emplacementBottle The emplacement to increase the quantity in.
	 *                          The emplacement must be in the wall and contain the bottle.
	 * @param bottleQuantity The bottle to increase the quantity of.
	 *               The bottle must be in the emplacement.
	 *
	 * @return The id of the updated cellar if the quantity is greater than 0 and the field has been updated otherwise throws a BadArgumentsException.
	 */
	ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException;

	/**
	 * Get all public cellars.
	 *
	 * @return A list of all public cellars if there are any, an empty list otherwise.
	 */
	List<Cellar> getPublicCellars() throws NotFoundException;

	/**
	 * Get all the cellars of a user.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the cellars of the user if there are any, an empty list otherwise.
	 */
	List<Cellar> getCellarsFromUser(ObjectId userId) throws NotFoundException;

	/**
	 * Get all the cellars where the user is a reader.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the cellars where the user is a reader if there are any, an empty list otherwise.
	 */
	List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws NotFoundException;

	/**
	 * Get cellars where the user is a manager.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the cellars where the user is a manager if there are any, an empty list otherwise.
	 */
	List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws NotFoundException;

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
	 * Insert a bottle to a cellar.
	 *
	 * @param wall The wall to add the bottle to.
	 * @param cellar The cellar to add the bottle to.
	 * @param bottle The bottle to insert.
	 * @param emplacementBottle The emplacement of the bottle.
	 *
	 * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
	 */
	ObjectId insertBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle) throws BadArgumentsException;

	/**
	 * Get all bottles from a cellar.
	 *
	 * @param cellarId The id of the cellar to get the bottles from.
	 *                 The cellar must exist.
	 *
	 * @return A list of all bottles from the cellar if there is at least one, otherwise throws NotFoundException.
	 */
	List<Bottle> getBottlesFromCellar(ObjectId cellarId) throws NotFoundException;

	/**
	 * Update a bottle in a cellar.
	 *
	 * @param wall The wall to update the bottle in.
	 * @param cellar The cellar to update the bottle in.
	 * @param bottle The bottle to update.
	 * @param emplacementBottle The emplacement of the bottle.
	 * @param updatedBottle The updated bottle.
	 *
	 * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
	 *
	 * @throws BadArgumentsException If the update was not successful.
	 */
	ObjectId updateBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle, Bottle updatedBottle) throws BadArgumentsException;

	/**
	 * Delete a bottle from a cellar.
	 *
	 * @param wall The wall to remove the bottle from.
	 * @param cellar The cellar to remove the bottle from.
	 * @param bottle The bottle to remove.
	 * @param emplacementBottle The emplacement to remove the bottle from.
	 *
	 * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
	 */
	ObjectId deleteBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle) throws BadArgumentsException;

	/**
	 * Insert a company.
	 *
	 * @param company The company to insert.
	 * @return The id of the inserted company.
	 */
	ObjectId insertOneCompany(Company company);

	/**
	 * Get all companies.
	 *
	 * @return A list of companys.
	 */
	List<Company> getCompanyList();

	/**
	 * Get a company by its id.
	 *
	 * @param id The id of the company.
	 *
	 * @return The company or null if not found.
	 */
	Company getOneCompany(ObjectId id);

	/**
	 * Update a company.
	 *
	 * @param id The id of the company to update.
	 * @param company The new company.
	 *
	 * @return true if the company has been updated, false otherwise.
	 */
	boolean updateOneCompany(ObjectId id, Company company);

	/**
	 * Delete a company.
	 *
	 * @param id The id of the company to delete.
	 *
	 * @return true if the company has been deleted, false otherwise.
	 */
	boolean deleteOneCompany(ObjectId id);

}
