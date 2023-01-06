package facade;

import exception.user.MustBeAnAdminException;
import exception.user.NoLoggedUser;
import org.bson.types.ObjectId;

import exception.BadArgumentsException;
import exception.InvalidUsernameException;
import exception.NotFoundException;

import persistence.entity.advertising.Advertising;
import persistence.entity.guide.Guide;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import persistence.entity.company.Company;
import persistence.entity.partner.Partner;
import persistence.entity.referencing.Referencing;
import persistence.entity.rate.Rate;
import persistence.entity.user.User;

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
	 * Add a view to an advertising.
	 *
	 * @param id The id of the advertising.
	 * @return true if the view was added to the advertising, false otherwise.
	 */
	boolean addView(ObjectId id);

	/**
	 * Validate an advertising.
	 *
	 * @param id The id of the advertising to validate.
	 * @return true if the advertising has been validated, false otherwise.
	 */
	boolean validateAdvertising(ObjectId id);

	/**
	 * Get advertising by their company id.
	 *
	 * @param company The id of the advertised company.
	 * @return A list of advertisings.
	 */
	public List<Advertising> getAdvertisingByCompany(ObjectId company);

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

	/**
	 * Logout the logged user.
	 */
	void logout();

	User getOneUser(ObjectId id);

	/**
	 * Get the logged user.
	 *
	 * @return The logged user.
	 * @throws NoLoggedUser if there is no user logged.
	 */
	User getLoggedUser() throws NoLoggedUser;

	User getOneUserByUsername(String username);

	boolean updateOneUser(ObjectId id, User user);

	/**
	 * Delete a user by its username.
	 *
	 * @param username The username of the user to delete.
	 * @return true if the user has been deleted, false otherwise.
	 * @throws MustBeAnAdminException if the user is not an admin.
	 */
	boolean deleteOneUser(String username) throws MustBeAnAdminException;

	/**
	 * Check if there is a user logged in.
	 *
	 * @return true if there is a user logged in, false otherwise.
	 */
	boolean isUserLogged();

	/**
	 * Check if there is an admin logged in.
	 *
	 * @return true if the user is an admin, false otherwise.
	 */
	boolean isAdminLogged();

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

	/**
	 * Get a random validated referencing.
	 *
	 * @return A Referencing.
	 */
	Referencing getRandomReferencing();

	/**
	 * Calculate the price of a referencing.
	 *
	 * @param startDate The start date of the referencing.
	 * @param endDate The end date of the referencing.
	 * @return The price.
	 */
	double calculatePrice(Date startDate, Date endDate, int importanceLevel);

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
	 * Return the companies where the user is a manager or masterManager.
	 *
	 * @param userId The id of the user.
	 *
	 * @return The list of companies where the user is a manager or masterManager.
	 */
	List<Company> findAllCompaniesByUserId(ObjectId userId);

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

	/**
	 * Return the list of companies that are accessible.
	 *
	 * @return The list of accessible companies if any, throws a NotFoundException otherwise.
	 */
	List<Company> findAllAccessibleCompanies();

	/**
	 * Return the list of companies that are not accessible.
	 *
	 * @return The list of companies that are not accessible if there are any, a NotFoundException is thrown otherwise.
	 */
	List<Company> findAllUnaccessibleCompanies();

	/**
	 * Add a manager to a company.
	 *
	 * @param companyId The id of the company.
	 * @param managerId The id of the manager to add.
	 *
	 * @return The id of the updated company if the manager was added, throws a BadArgumentsException otherwise.
	 * @throws BadArgumentsException if the manager was not added.
	 */
	ObjectId addManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException;

	/**
	 * Removes a manager from a company.
	 *
	 * @param companyId The id of the company.
	 * @param managerId The id of the manager.
	 *
	 * @return The id of the company if the manager was removed successfully, else throws a BadArgumentsException.
	 * @throws BadArgumentsException If the company or the manager does not exist.
	 */
	ObjectId removeManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException;

	/**
	 * Refuse a request to publish a new Company.
	 *
	 * @param companyId The id of the company to refuse.
	 *
	 * @return The id of the refused company if the operation was successful, else throw an exception.
	 * @throws BadArgumentsException If the company does not exist or if the company has already been accepted by an Admin.
	 */
	ObjectId refuseRequest(ObjectId companyId) throws BadArgumentsException;

	/**
	 *  Accept a request to create a Company.
	 *
	 * @param companyId The id of the company to accept.
	 *
	 * @return The id of the accepted company if the operation is successful, else throws an exception.
	 * @throws BadArgumentsException If the company does not exist or if the company is already accepted.
	 */
	ObjectId acceptRequest(ObjectId companyId) throws BadArgumentsException;

	/**
	 * Promote a user to masterManager.
	 *
	 * @param companyId The id of the company.
	 * @param newMasterManagerId The id of the new masterManager.
	 *
	 * @return The id of the updated company if the promotion was successful, throws a BadArgumentsException otherwise.
	 * @throws BadArgumentsException if the user or the company doesn't exist.
	 */
	ObjectId promoteNewMasterManager(ObjectId companyId, ObjectId newMasterManagerId) throws BadArgumentsException;

	/**
	 * Insert a rate.
	 *
	 * @param rate The partner to insert.
	 * @return The id of the inserted rate.
	 */
	ObjectId insertOneRate(Rate rate);

	/**
	 * Get a rate by its id.
	 *
	 * @param id The id of the rate.
	 * @return The rate.
	 */
	Rate getOneRate(ObjectId id);

	/**
	 * Get all rates.
	 *
	 * @return A list of rates.
	 */
	List<Rate> getRateList();

	/**
	 * Get all the rates of a user.
	 *
	 * @param userId The id of the user.
	 *
	 * @return A list of all the rates of the user if there are any, an empty list otherwise.
	 */
	List<Rate> getRateListFromUser(ObjectId userId) throws NotFoundException;

	/**
	 * Update a rate.
	 *
	 * @param id      The id of the rate to update.
	 * @param rate The rate to update.
	 * @return The number of updated rates.
	 */
	boolean updateOneRate(ObjectId id, Rate rate);

	/**
	 * Delete a rate.
	 *
	 * @param id The id of the rate to delete.
	 * @return The number of deleted rates.
	 */
	boolean deleteOneRate(ObjectId id);
}
