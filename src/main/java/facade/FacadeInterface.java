package facade;

import org.bson.types.ObjectId;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
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
	 * @return true if the cellar has been updated, false otherwise.
	 */
	boolean updateOneCellar(ObjectId id, Cellar cellar);

	/**
	 * Delete a cellar.
	 *
	 * @param id The id of the cellar to delete.
	 * @return true if the cellar has been deleted, false otherwise.
	 */
	boolean deleteOneCellar(ObjectId id);

	/**
	 * Add a cellar reader.
	 *
	 * @param user The user to add to readers.
	 * @param cellar The cellar to add the user to.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId addCellarReader(ObjectId user, ObjectId cellar);

	/**
	 * Remove a cellar reader.
	 *
	 * @param user The user to remove from readers.
	 * @param cellar The cellar to remove the user from.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId removeCellarReader(ObjectId user, ObjectId cellar);

	/**
	 * Add a cellar manager.
	 *
	 * @param user The user to add to managers.
	 * @param cellar The cellar to add the user to.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId addCellarManager(ObjectId user, ObjectId cellar);

	/**
	 * Remove a cellar manager.
	 *
	 * @param user The user to remove from managers.
	 * @param cellar The cellar to remove the user from.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId removeCellarManager(ObjectId user, ObjectId cellar);

	/**
	 * Add a wall to a cellar.
	 *
	 * @param cellar The cellar to add the wall to.
	 * @param wall The wall to add.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId addWall(Wall wall, ObjectId cellar);

	/**
	 * Remove a wall from a cellar.
	 *
	 * @param cellar The cellar to remove the wall from.
	 * @param wall The wall to remove.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId removeWall(Wall wall, ObjectId cellar);

	/**
	 * Add a bottle to a cellar.
	 *
	 * @param wall The wall to add the bottle to.
	 * @param cellar The cellar to add the bottle to.
	 * @param bottle The bottle to add.
	 * @param emplacementBottle The emplacement of the bottle.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId addBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle);

	/**
	 * Remove a bottle from a cellar.
	 *
	 * @param wall The wall to remove the bottle from.
	 * @param cellar The cellar to remove the bottle from.
	 * @param bottle The bottle to remove.
	 * @param emplacementBottle The emplacement to remove the bottle from.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId removeBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle);

	/**
	 * Add an emplacement to a wall.
	 *
	 * @param cellar The cellar to add the emplacement to.
	 * @param wall The wall to add the emplacement to.
	 * @param emplacementBottle The emplacement to add.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle);

	/**
	 * Remove an emplacement from a wall.
	 *
	 * @param cellar The cellar to remove the emplacement from.
	 * @param wall The wall to remove the emplacement from.
	 * @param emplacementBottle The emplacement to remove.
	 *
	 * @return The id of the updated cellar if the update was successful, null otherwise.
	 */
	ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle);

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
	 * @return The id of the updated cellar if the bottle was found and updated, null otherwise.
	 */
	ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity);

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
	 * @return The id of the updated cellar if the quantity is greater than 0 and the field has been updated, null otherwise.
	 */
	ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity);

	/**
	 * Get all public cellars.
	 *
	 * @return A list of all public cellars.
	 */
	List<Cellar> getPublicCellars() throws Exception;

	/**
	 * Get all the cellars of a user.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the cellars of the user.
	 */
	List<Cellar> getCellarsFromUser(ObjectId userId) throws Exception;

	/**
	 * Get all the cellars where the user is a reader.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the cellars where the user is a reader.
	 */
	List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws Exception;

	/**
	 * Get cellars where the user is a manager.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the cellars where the user is a manager.
	 */
	List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws Exception;
}
