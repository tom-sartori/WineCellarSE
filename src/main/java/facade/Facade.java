package facade;


import exception.BadArgumentsException;
import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import exception.user.MustBeAnAdminException;
import exception.user.NoLoggedUser;
import logic.controller.advertising.AdvertisingController;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import persistence.entity.event.Event;
import persistence.entity.guide.Guide;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import persistence.entity.company.Company;
import persistence.entity.notification.Notification;
import persistence.entity.partner.Partner;
import persistence.entity.rate.Rate;
import persistence.entity.referencing.Referencing;
import persistence.entity.user.Friend;
import persistence.entity.user.User;

import java.util.Date;
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
     * Insert a advertising.
     *
     * @param advertising The advertising to insert.
     * @return The id of the inserted advertising.
     */
    public ObjectId insertOneAdvertising(Advertising advertising) {
        return AdvertisingController.getInstance().insertOne(advertising);
    }

    /**
     * Get all advertisings.
     *
     * @return A list of advertisings.
     */
    public List<Advertising> getAdvertisingList() {
        return AdvertisingFacade.getInstance().getAdvertisingList();
    }

    /**
     * Get an advertising by its id.
     *
     * @param id The id of the advertising.
     * @return The advertising or null if not found.
     */
    public Advertising getOneAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().getOneAdvertising(id);
    }

    /**
     * Update a advertising.
     *
     * @param id The id of the advertising to update.
     * @param advertising The new advertising.
     * @return true if the advertising has been updated, false otherwise.
     */
    public boolean updateOneAdvertising(ObjectId id, Advertising advertising) {
        return AdvertisingFacade.getInstance().updateOneAdvertising(id, advertising);
    }

    /**
     * Delete a advertising.
     *
     * @param id The id of the advertising to delete.
     * @return true if the advertising has been deleted, false otherwise.
     */
    public boolean deleteOneAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().deleteOneAdvertising(id);
    }

    /**
     * Renew an advertising.
     *
     * @param id The id of the advertising to renew.
     * @param endDate The new end date of the advertising.
     * @return true if the advertising has been renewed, false otherwise.
     */
    public boolean renewOneAdvertising(ObjectId id, Date endDate) {
        return AdvertisingFacade.getInstance().renewOneAdvertising(id, endDate);
    }

    /**
     * Pay for an advertising.
     *
     * @param id The id of the advertising to pay for.
     * @return true if the advertising has been paid, false otherwise.
     */
    public boolean payOneAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().payOneAdvertising(id);
    }

    /**
     * Add a view to an advertising.
     *
     * @param id The id of the advertising.
     * @return true if the view was added to the advertising, false otherwise.
     */
    public boolean addView(ObjectId id) {
        return AdvertisingFacade.getInstance().addView(id);
    }

    /**
     * Validate an advertising.
     *
     * @param id The id of the advertising to validate.
     * @return true if the advertising has been validated, false otherwise.
     */
    public boolean validateAdvertising(ObjectId id) {
        return AdvertisingFacade.getInstance().validateAdvertising(id);
    }

    /**
     * Get advertisings by their company id.
     *
     * @param company The id of the advertised company.
     * @return A list of advertisings.
     */
    public List<Advertising> getAdvertisingsByCompany(ObjectId company) {
        return AdvertisingFacade.getInstance().getAdvertisingsByCompany(company);
    }

    /**
     * Get advertisings not validated.
     *
     * @return A list of advertisings.
     */
    public List<Advertising> getNotValidatedAdvertisings() {
        return AdvertisingFacade.getInstance().getNotValidatedAdvertisings();
    }

    /**
     * Get not validated advertisings by their company id.
     *
     * @param company The id of the advertised company.
     * @return A list of advertisings.
     */
    public List<Advertising> getNotValidatedAdvertisingsByCompany(ObjectId company) {
        return AdvertisingFacade.getInstance().getNotValidatedAdvertisingsByCompany(company);
    }

    /**
     * Get validated advertisings by their company id.
     *
     * @param company The id of the advertised company.
     * @return A list of advertisings.
     */
    public List<Advertising> getValidatedAdvertisingsByCompany(ObjectId company) {
        return AdvertisingFacade.getInstance().getValidatedAdvertisingsByCompany(company);
    }

    /**
     * Get a random validated advertising.
     *
     * @return An advertising.
     */
    public Advertising getRandomAdvertising() {
        return AdvertisingFacade.getInstance().getRandomAdvertising();
    }

    /**
     * Calculate the price of an advertising.
     *
     * @param startDate The start date of the advertising.
     * @param endDate The end date of the advertising.
     * @return The price.
     */
    public double calculatePriceAdvertising(Date startDate, Date endDate) {
        return AdvertisingFacade.getInstance().calculatePriceAdvertising(startDate,endDate);
    }

    /**
     * Insert a guide.
     *
     * @param guide The partner to insert.
     * @return The id of the inserted guide.
     */
    @Override
    public ObjectId insertOneGuide(Guide guide) {
        return GuideFacade.getInstance().insertOneGuide(guide);
    }

    /**
     * Get all guides.
     *
     * @return A list of guides.
     */
    @Override
    public List<Guide> getGuideList() {
        return GuideFacade.getInstance().getGuideList();
    }

    /**
     * Get a guide by its id.
     *
     * @param id The id of the guide.
     * @return The guide.
     */
    @Override
    public Guide getOneGuide(ObjectId id) {
        return GuideFacade.getInstance().getOneGuide(id);
    }

    /**
     * Update a guide.
     *
     * @param id The id of the guide to update.
     * @param guide The new guide.
     * @return true if the guide has been updated, false otherwise.
     */
    @Override
    public boolean updateOneGuide(ObjectId id, Guide guide) {
        return GuideFacade.getInstance().updateOneGuide(id, guide);
    }

    /**
     * Delete a guide.
     *
     * @param id The id of the guide to delete.
     * @return true if the guide has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOneGuide(ObjectId id) {
        return GuideFacade.getInstance().deleteOneGuide(id);
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
     * Logout the logged user.
     */
    @Override
    public void logout() {
        UserFacade.getInstance().logout();
    }

    /**
     * Add a friend to the logged user.
     *
     * @param username of the friend to add.
     * @return the friend requested.
     * @throws NotFoundException if the friend is not found.
     * @throws NoLoggedUser if there is no user logged.
     */
    @Override
    public User addFriend(String username) throws NotFoundException, NoLoggedUser {
        return UserFacade.getInstance().addFriend(username);
    }

    /**
     * Accept a friend request.
     *
     * @param username of the friend to accept.
     * @throws NoLoggedUser if there is no user logged.
     */
    @Override
    public void acceptFriend(String username) throws NoLoggedUser {
        UserFacade.getInstance().acceptFriend(username);
    }

    /**
     * Remove a friend from the logged user.
     *
     * @param username of the friend to remove.
     * @return true if the friend has been removed, false otherwise.
     * @throws NoLoggedUser if there is no user logged.
     */
    @Override
    public boolean removeFriend(String username) throws NoLoggedUser {
        return UserFacade.getInstance().removeFriend(username);
    }

    /**
     * Return the list of friends of the logged user.
     *
     * @param onlyAcceptedFriend True if you want only the accepted friends. False if you want all the friends.
     * @return The list of friends of the logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    @Override
    public List<Friend> getFriendList(boolean onlyAcceptedFriend) throws NoLoggedUser {
        return UserFacade.getInstance().getFriendList(onlyAcceptedFriend);
    }

    /**
     * Return the list of friend requests of the logged user.
     *
     * @return The list of friend requests for the logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
	@Override
	public List<Friend> getFriendRequestList() throws NoLoggedUser {
		return UserFacade.getInstance().getFriendRequestList();
	}

    /**
     * Refresh the logged user with the db.
     *
     * @throws NoLoggedUser if there is no user logged.
     */
    @Override
    public void refreshLoggedUser() throws NoLoggedUser {
        UserFacade.getInstance().refreshLoggedUser();
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
     * Get the logged user.
     *
     * @return The logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    @Override
    public User getLoggedUser() throws NoLoggedUser {
        return UserFacade.getInstance().getLoggedUser();
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

    /**
     * Delete a user by its id.
     *
     * @param id The id of the user to delete.
     * @return true if the user has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOneUser(ObjectId id) {
        return UserFacade.getInstance().deleteOneUser(id);
    }

    /**
     * Delete a user by its username.
     *
     * @param username The username of the user to delete.
     * @return true if the user has been deleted, false otherwise.
     * @throws MustBeAnAdminException if the user is not an admin.
     */
    @Override
    public boolean deleteOneUser(String username) throws MustBeAnAdminException {
        return UserFacade.getInstance().deleteOneUser(username);
    }

    /**
     * Check if there is a user logged in.
     *
     * @return true if there is a user logged in, false otherwise.
     */
    @Override
    public boolean isUserLogged() {
        return UserFacade.getInstance().isUserLogged();
    }

    /**
     * Check if the user logged in is an admin.
     *
     * @return true if the user is an admin, false otherwise.
     */
    @Override
    public boolean isAdminLogged() {
        return UserFacade.getInstance().isAdminLogged();
    }

    /**
     * Insert a notification.
     *
     * @param notification The notification to insert.
     * @return The id of the inserted notification.
     */
    @Override
    public ObjectId insertOneNotification(Notification notification) {
        return NotificationFacade.getInstance().insertOneNotification(notification);
    }

    /**
     * Get all notifications.
     *
     * @return A list of notifications.
     */
    @Override
    public List<Notification> getNotificationList() {
        return NotificationFacade.getInstance().getNotificationList();
    }

    /**
     * Get a notification by its id.
     *
     * @param id The id of the notification.
     * @return The notification.
     */
    @Override
    public Notification getOneNotification(ObjectId id) {
        return NotificationFacade.getInstance().getOneNotification(id);
    }

    /**
     * Get all the notifications of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the notifications of the user.
     */
    @Override
    public List<Notification> getNotificationListFromUser(ObjectId userId) throws Exception {
        return NotificationFacade.getInstance().getNotificationListFromUser(userId);
    }

    /**
     * Update a notification.
     *
     * @param id The id of the notification to update.
     * @param notification The new notification.
     * @return true if the notification has been updated, false otherwise.
     */
    @Override
    public boolean updateOneNotification(ObjectId id, Notification notification) {
        return NotificationFacade.getInstance().updateOneNotification(id, notification);
    }

    /**
     * Delete a notification.
     *
     * @param id The id of the notification to delete.
     * @return true if the notification has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOneNotification(ObjectId id) {
        return NotificationFacade.getInstance().deleteOneNotification(id);
    }

    /**
     * Insert a referencing.
     *
     * @param referencing The referencing to insert.
     * @return The id of the inserted referencing.
     */
    @Override
    public ObjectId insertOneReferencing(Referencing referencing) {
        return ReferencingFacade.getInstance().insertOneReferencing(referencing);
    }

    /**
     * Get all referencings.
     *
     * @return A list of referencings.
     */
    @Override
    public List<Referencing> getReferencingList() {
        return ReferencingFacade.getInstance().getReferencingList();
    }

    /**
     * Get a referencing by its id.
     *
     * @param id The id of the referencing.
     * @return The referencing or null if not found.
     */
    @Override
    public Referencing getOneReferencing(ObjectId id) {
        return ReferencingFacade.getInstance().getOneReferencing(id);
    }

    /**
     * Get referencings by their importanceLevel.
     *
     * @param importanceLevel The level of importance of the searched referencings.
     * @return A list of referencings.
     */
    @Override
    public List<Referencing> getReferencingByLevel(int importanceLevel) {
        return ReferencingFacade.getInstance().getReferencingByLevel(importanceLevel);
    }

    /**
     * Get referencings by their company id.
     *
     * @param company The id of the referenced company.
     * @return A list of referencings.
     */
    @Override
    public List<Referencing> getReferencingsByCompany(ObjectId company) {
        return ReferencingFacade.getInstance().getReferencingsByCompany(company);
    }

    /**
     * Update a referencing.
     *
     * @param id The id of the referencing to update.
     * @param referencing The new referencing.
     * @return true if the referencing has been updated, false otherwise.
     */
    @Override
    public boolean updateOneReferencing(ObjectId id, Referencing referencing) {
        return ReferencingFacade.getInstance().updateOneReferencing(id, referencing);
    }

    /**
     * Delete a referencing.
     *
     * @param id The id of the referencing to delete.
     * @return true if the referencing has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOneReferencing(ObjectId id) {
        return ReferencingFacade.getInstance().deleteOneReferencing(id);
    }

    /**
     * Update the status of a referencing.
     *
     * @param id The id of the referencing to update.
     * @param referencing The new referencing.
     * @return true if the referencing has been updated, false otherwise.
     */
    @Override
    public boolean updateStatus(ObjectId id, Referencing referencing){ return ReferencingFacade.getInstance().updateStatus(id, referencing);}

    /**
     * Get a random validated referencing.
     *
     * @return A Referencing.
     */
    @Override
    public Referencing getRandomReferencing() {
        return ReferencingFacade.getInstance().getRandomReferencing();
    }

    /**
     * Calculate the price of a referencing.
     *
     * @param startDate The start date of the referencing.
     * @param endDate The end date of the referencing.
     * @return The price.
     */
    @Override
    public double calculatePriceReferencing(Date startDate, Date endDate, int importanceLevel) {
        return ReferencingFacade.getInstance().calculatePriceReferencing(startDate,endDate,importanceLevel);
    }

    /**
     * Get referencings by their company id and the status.
     *
     * @param company The id of the referenced company.
     * @param status The status of the referencing.
     * @return A list of referencings.
     */
    @Override
    public List<Referencing> getReferencingsByCompanyByStatus(ObjectId company, String status) {
        return ReferencingFacade.getInstance().getReferencingsByCompanyByStatus(company, status);
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
     * Return the companies where the user is a manager or masterManager.
     *
     * @param userId The id of the user.
     *
     * @return The list of companies where the user is a manager or masterManager.
     */
    public List<Company> findAllCompaniesByUserId(ObjectId userId) {
        return CompanyFacade.getInstance().findAllCompaniesByUserId(userId);
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

    /**
     * Insert a rate.
     *
     * @param rate The rate to insert.
     * @return The id of the inserted rate.
     */
    @Override
    public ObjectId insertOneRate(Rate rate) {
        return RateFacade.getInstance().insertOneRate(rate);
    }

    /**
     * Get all rates.
     *
     * @return A list of rates.
     */
    @Override
    public List<Rate> getRateList() {
        return RateFacade.getInstance().getRateList();
    }

    /**
     * Get a rate by its id.
     *
     * @param id The id of the rate.
     * @return The rate.
     */
    @Override
    public Rate getOneRate(ObjectId id) {
        return RateFacade.getInstance().getOneRate(id);
    }

    /**
     * Get all the rates of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the rates of the user if there are any, otherwise throws a NotFoundException.
     */
    public List<Rate> getRateListFromUser(ObjectId userId) throws NotFoundException {
        return RateFacade.getInstance().getRateListFromUser(userId);
    }

    /**
     * Update a rate.
     *
     * @param id The id of the rate to update.
     * @param rate The new rate.
     * @return true if the rate has been updated, false otherwise.
     */
    @Override
    public boolean updateOneRate(ObjectId id, Rate rate) {
        return RateFacade.getInstance().updateOneRate(id, rate);
    }

    /**
     * Delete a rate.
     *
     * @param id The id of the rate to delete.
     * @return true if the rate has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOneRate(ObjectId id) {
        return RateFacade.getInstance().deleteOneRate(id);
    }

    /**
     * Get all events.
     *
     * @return A list of events.
     */
    public List<Event> getEventList() {
        return EventFacade.getInstance().getEventList();
    }

    /**
     * Insert an event.
     *
     * @param event The event to insert.
     * @return The id of the inserted event.
     */
    public ObjectId insertOneEvent(Event event) {
        return EventFacade.getInstance().insertOneEvent(event);
    }

    /**
     * Get an event by its id.
     *
     * @param id The id of the event.
     * @return The event or null if not found.
     */
    public Event getOneEvent(ObjectId id) {
        return EventFacade.getInstance().getOneEvent(id);
    }

    /**
     * Update an event.
     *
     * @param id The id of the event to update.
     * @param event The new event.
     * @return true if the event has been updated, false otherwise.
     */
    public boolean updateOneEvent(ObjectId id, Event event) {
        return EventFacade.getInstance().updateOneEvent(id, event);
    }

    /**
     * Delete an event.
     *
     * @param id The id of the event to delete.
     * @return true if the event has been deleted, false otherwise.
     */
    public boolean deleteOneEvent(ObjectId id) {
        return EventFacade.getInstance().deleteOneEvent(id);
    }

    /**
     * Get events by their company id.
     *
     * @param company The id of the company.
     * @return A list of events.
     */
    public List<Event> getEventsByCompany(ObjectId company) {
        return EventFacade.getInstance().getEventsByCompany(company);
    }
}
