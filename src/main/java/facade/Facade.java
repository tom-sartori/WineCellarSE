package facade;

import exception.BadArgumentsException;
import exception.BadCredentialException;
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
 * Global facade class.
 */
public class Facade implements FacadeInterface {

    /**
     * Singleton instance.
     */
    private static Facade instance;

    private Facade() { }

    /**
     * Get the singleton instance of the facade.
     *
     * @return The singleton instance.
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * Insert a partner.
     *
     * @param partner The partner to insert.
     * @return The id of the inserted partner.
     */
    @Override
    public ObjectId insertOnePartner(Partner partner) {
        return PartnerFacade.getInstance().insertOnePartner(partner);
    }

    /**
     * Get all partners.
     *
     * @return A list of partners.
     */
    @Override
    public List<Partner> getPartnerList() {
        return PartnerFacade.getInstance().getPartnerList();
    }

    /**
     * Get a partner by its id.
     *
     * @param id The id of the partner.
     * @return The partner.
     */
    @Override
    public Partner getOnePartner(ObjectId id) {
        return PartnerFacade.getInstance().getOnePartner(id);
    }

    /**
     * Update a partner.
     *
     * @param id The id of the partner to update.
     * @param partner The new partner.
     * @return true if the partner has been updated, false otherwise.
     */
    @Override
    public boolean updateOnePartner(ObjectId id, Partner partner) {
        return PartnerFacade.getInstance().updateOnePartner(id, partner);
    }

    /**
     * Delete a partner.
     *
     * @param id The id of the partner to delete.
     * @return true if the partner has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOnePartner(ObjectId id) {
        return PartnerFacade.getInstance().deleteOnePartner(id);
    }

    /**
     * Insert a cellar.
     *
     * @param cellar The cellar to insert.
     * @return The id of the inserted cellar.
     */
    @Override
    public ObjectId insertOneCellar(Cellar cellar) {
        return CellarFacade.getInstance().insertOneCellar(cellar);
    }

    /**
     * Get all Cellars.
     *
     * @return A list of cellars.
     */
    @Override
    public List<Cellar> getCellarList() {
        return CellarFacade.getInstance().getCellarList();
    }

    /**
     * Get a cellar by its id.
     *
     * @param id The id of the cellar.
     * @return The cellar or null if not found.
     */
    @Override
    public Cellar getOneCellar(ObjectId id) {
        return CellarFacade.getInstance().getOneCellar(id);
    }

    /**
     * Update a cellar.
     *
     * @param id The id of the cellar to update.
     * @param cellar The new cellar.
     * @return true if the cellar has been updated, false otherwise.
     */
    @Override
    public boolean updateOneCellar(ObjectId id, Cellar cellar) {
        return CellarFacade.getInstance().updateOneCellar(id, cellar);
    }

    /**
     * Delete a cellar.
     *
     * @param id The id of the cellar to delete.
     * @return true if the cellar has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOneCellar(ObjectId id) {
        return CellarFacade.getInstance().deleteOneCellar(id);
    }

    /**
     * Add a cellar reader.
     *
     * @param user The user to add to readers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId addCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException {
        return CellarFacade.getInstance().addCellarReader(user,cellar);
    }

    /**
     * Remove a cellar reader.
     *
     * @param user The user to remove from readers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId removeCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException{
        return CellarFacade.getInstance().removeCellarReader(user, cellar);
    }

    /**
     * Add a cellar manager.
     *
     * @param user The user to add to managers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId addCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException {
        return CellarFacade.getInstance().addCellarManager(user, cellar);
    }

    /**
     * Remove a cellar manager.
     *
     * @param user The user to remove from managers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId removeCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException{
        return CellarFacade.getInstance().removeCellarManager(user, cellar);
    }

    /**
     * Add a wall to a cellar.
     *
     * @param cellar The cellar to add the wall to.
     * @param wall The wall to add.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId addWall(Wall wall, ObjectId cellar) throws BadArgumentsException {
        return CellarFacade.getInstance().addWall(wall,cellar);
    }

    /**
     * Remove a wall from a cellar.
     *
     * @param cellar The cellar to remove the wall from.
     * @param wall The wall to remove.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId removeWall(Wall wall, ObjectId cellar) throws BadArgumentsException {
        return CellarFacade.getInstance().removeWall(wall,cellar);
    }

    /**
     * Add an emplacement to a wall.
     *
     * @param cellar The cellar to add the emplacement to.
     * @param wall The wall to add the emplacement to.
     * @param emplacementBottle The emplacement to add.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException {
        return CellarFacade.getInstance().addEmplacement(cellar, wall, emplacementBottle);
    }

    /**
     * Remove an emplacement from a wall.
     *
     * @param cellar The cellar to remove the emplacement from.
     * @param wall The wall to remove the emplacement from.
     * @param emplacementBottle The emplacement to remove.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException{
        return CellarFacade.getInstance().removeEmplacement(cellar, wall, emplacementBottle);
    }

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
     * @return The id of the updated cellar if the bottle was found and updated, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException {
        return CellarFacade.getInstance().increaseBottleQuantity(cellar, wall, emplacementBottle, bottleQuantity);
    }

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
     * @return The id of the updated cellar if the quantity is greater than 0 and the field has been updated, otherwise throws a BadArgumentsException.
     */
    @Override
    public ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException{
        return CellarFacade.getInstance().decreaseBottleQuantity(cellar, wall, emplacementBottle, bottleQuantity);
    }

    /**
     * Get all public cellars.
     *
     * @return A list of all public cellars if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getPublicCellars() throws NotFoundException {
        return CellarFacade.getInstance().getPublicCellars();
    }

    /**
     * Get all the cellars of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars of the user if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getCellarsFromUser(ObjectId userId) throws NotFoundException {
        return CellarFacade.getInstance().getCellarsFromUser(userId);
    }

    /**
     * Get all the cellars where the user is a reader.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a reader if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws NotFoundException {
        return CellarFacade.getInstance().getReadOnlyCellarsFromUser(userId);
    }

    /**
     * Get cellars where the user is a manager.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a manager if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws NotFoundException {
        return CellarFacade.getInstance().getCellarsWhereUserIsManager(userId);
    }

    @Override
    public ObjectId register(User user) throws InvalidUsernameException {
        return UserFacade.getInstance().register(user);
    }

    /**
     * Login a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user if the login is successful.
     */
    @Override
    public User login(String username, String password) throws BadCredentialException {
        return UserFacade.getInstance().login(username, password);
    }

    /**
     * Get one user by its id.
     * @param id The id of the user.
     * @return The user.
     */
    @Override
    public User getOneUser(ObjectId id) {
        return UserFacade.getInstance().getOneUser(id);
    }

    /**
     * Get a user by its username.
     * @param username The username of the user to find.
     * @return The user.
     * @throws NotFoundException if the user is not found.
     */
    @Override
    public User getOneUserByUsername(String username) throws NotFoundException {
        return UserFacade.getInstance().getOneUserByUsername(username);
    }

    /**
     * Update a user.
     *
     * @param id The id of the user to update.
     * @param user The new user.
     * @return true if the user has been updated, false otherwise.
     */
    @Override
    public boolean updateOneUser(ObjectId id, User user) {
        return UserFacade.getInstance().updateOneUser(id, user);
    }

    @Override
    public boolean deleteOneUser(ObjectId id) {
        return UserFacade.getInstance().deleteOneUser(id);
    }

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
    public ObjectId insertBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle) throws BadArgumentsException {
        return BottleFacade.getInstance().insertBottle(wall, cellar, bottle, emplacementBottle);
    }

    /**
     * Get all bottles from a cellar.
     *
     * @param cellarId The id of the cellar to get the bottles from.
     *                 The cellar must exist.
     *
     * @return A list of all bottles from the cellar if there is at least one, otherwise throws NotFoundException.
     */
    public List<Bottle> getBottlesFromCellar(ObjectId cellarId) throws NotFoundException {
        return BottleFacade.getInstance().getBottlesFromCellar(cellarId);
    }

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
    public ObjectId updateBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle, Bottle updatedBottle) throws BadArgumentsException {
        return BottleFacade.getInstance().updateBottle(wall, cellar, bottle, emplacementBottle, updatedBottle);
    }

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
    public ObjectId deleteBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle) throws BadArgumentsException {
        return BottleFacade.getInstance().deleteBottle(wall, cellar, bottle, emplacementBottle);
    }

    /**
     * Insert a company.
     *
     * @param company The company to insert.
     * @return The id of the inserted company.
     */
    public ObjectId insertOneCompany(Company company) {
        return CompanyFacade.getInstance().insertOneCompany(company);
    }

    /**
     * Get all companies.
     *
     * @return A list of companys.
     */
    public List<Company> getCompanyList() {
        return CompanyFacade.getInstance().getCompanyList();
    }

    /**
     * Get a company by its id.
     *
     * @param id The id of the company.
     *
     * @return The company or null if not found.
     */
    public Company getOneCompany(ObjectId id) {
        return CompanyFacade.getInstance().getOneCompany(id);
    }

    /**
     * Update a company.
     *
     * @param id The id of the company to update.
     * @param company The new company.
     *
     * @return true if the company has been updated, false otherwise.
     */
    public boolean updateOneCompany(ObjectId id, Company company) {
        return CompanyFacade.getInstance().updateOneCompany(id, company);
    }

    /**
     * Delete a company.
     *
     * @param id The id of the company to delete.
     *
     * @return true if the company has been deleted, false otherwise.
     */
    public boolean deleteOneCompany(ObjectId id) {
        return CompanyFacade.getInstance().deleteOneCompany(id);
    }

    /**
     * Return the list of companies that are accessible.
     *
     * @return The list of accessible companies if any, throws a NotFoundException otherwise.
     */
    public List<Company> findAllAccessibleCompanies() throws NotFoundException {
        return CompanyFacade.getInstance().findAllAccessibleCompanies();
    }

    /**
     * Return the list of companies that are not accessible.
     *
     * @return The list of companies that are not accessible if there are any, a NotFoundException is thrown otherwise.
     */
    public List<Company> findAllUnaccessibleCompanies() throws NotFoundException {
        return CompanyFacade.getInstance().findAllUnaccessibleCompanies();
    }

    /**
     * Add a manager to a company.
     *
     * @param companyId The id of the company.
     * @param managerId The id of the manager to add.
     *
     * @return The id of the updated company if the manager was added, throws a BadArgumentsException otherwise.
     * @throws BadArgumentsException if the manager was not added.
     */
    public ObjectId addManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
        return CompanyFacade.getInstance().addManager(companyId, managerId);
    }

    /**
     * Removes a manager from a company.
     *
     * @param companyId The id of the company.
     * @param managerId The id of the manager.
     *
     * @return The id of the company if the manager was removed successfully, else throws a BadArgumentsException.
     * @throws BadArgumentsException If the company or the manager does not exist.
     */
    public ObjectId removeManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
        return CompanyFacade.getInstance().removeManager(companyId, managerId);
    }

    /**
     * Refuse a request to publish a new Company.
     *
     * @param companyId The id of the company to refuse.
     *
     * @return The id of the refused company if the operation was successful, else throw an exception.
     * @throws BadArgumentsException If the company does not exist or if the company has already been accepted by an Admin.
     */
    public ObjectId refuseRequest(ObjectId companyId) throws BadArgumentsException {
        return CompanyFacade.getInstance().refuseRequest(companyId);
    }

    /**
     *  Accept a request to create a Company.
     *
     * @param companyId The id of the company to accept.
     *
     * @return The id of the accepted company if the operation is successful, else throws an exception.
     * @throws BadArgumentsException If the company does not exist or if the company is already accepted.
     */
    public ObjectId acceptRequest(ObjectId companyId) throws BadArgumentsException {
        return CompanyFacade.getInstance().acceptRequest(companyId);
    }

    /**
     * Promote a user to masterManager.
     *
     * @param companyId The id of the company.
     * @param newMasterManagerId The id of the new masterManager.
     *
     * @return The id of the updated company if the promotion was successful, throws a BadArgumentsException otherwise.
     * @throws BadArgumentsException if the user or the company doesn't exist.
     */
    public ObjectId promoteNewMasterManager(ObjectId companyId, ObjectId newMasterManagerId) throws BadArgumentsException {
        return CompanyFacade.getInstance().promoteNewMasterManager(companyId, newMasterManagerId);
    }

}
