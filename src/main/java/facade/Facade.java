package facade;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import org.bson.types.ObjectId;
import persistence.dao.notification.NotificationDao;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;
import persistence.entity.notification.Notification;
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
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId addCellarReader(ObjectId user, ObjectId cellar) {
        return CellarFacade.getInstance().addCellarReader(user,cellar);
    }

    /**
     * Remove a cellar reader.
     *
     * @param user The user to remove from readers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId removeCellarReader(ObjectId user, ObjectId cellar) {
        return CellarFacade.getInstance().removeCellarReader(user, cellar);
    }

    /**
     * Add a cellar manager.
     *
     * @param user The user to add to managers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId addCellarManager(ObjectId user, ObjectId cellar) {
        return CellarFacade.getInstance().addCellarManager(user, cellar);
    }

    /**
     * Remove a cellar manager.
     *
     * @param user The user to remove from managers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId removeCellarManager(ObjectId user, ObjectId cellar) {
        return CellarFacade.getInstance().removeCellarManager(user, cellar);
    }

    /**
     * Add a wall to a cellar.
     *
     * @param cellar The cellar to add the wall to.
     * @param wall The wall to add.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId addWall(Wall wall, ObjectId cellar) {
        return CellarFacade.getInstance().addWall(wall,cellar);
    }

    /**
     * Remove a wall from a cellar.
     *
     * @param cellar The cellar to remove the wall from.
     * @param wall The wall to remove.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId removeWall(Wall wall, ObjectId cellar) {
        return CellarFacade.getInstance().removeWall(wall,cellar);
    }

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
    public ObjectId addBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle){
        return CellarFacade.getInstance().addBottle(wall, cellar, bottle, emplacementBottle);
    }

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
    public ObjectId removeBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle){
        return CellarFacade.getInstance().removeBottle(wall, cellar, bottle, emplacementBottle);
    }

    /**
     * Add an emplacement to a wall.
     *
     * @param cellar The cellar to add the emplacement to.
     * @param wall The wall to add the emplacement to.
     * @param emplacementBottle The emplacement to add.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle){
        return CellarFacade.getInstance().addEmplacement(cellar, wall, emplacementBottle);
    }

    /**
     * Remove an emplacement from a wall.
     *
     * @param cellar The cellar to remove the emplacement from.
     * @param wall The wall to remove the emplacement from.
     * @param emplacementBottle The emplacement to remove.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    @Override
    public ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle){
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
     * @return The id of the updated cellar if the bottle was found and updated, null otherwise.
     */
    @Override
    public ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity){
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
     * @return The id of the updated cellar if the quantity is greater than 0 and the field has been updated, null otherwise.
     */
    @Override
    public ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity){
        return CellarFacade.getInstance().decreaseBottleQuantity(cellar, wall, emplacementBottle, bottleQuantity);
    }

    /**
     * Get all public cellars.
     *
     * @return A list of all public cellars.
     */
    public List<Cellar> getPublicCellars() throws Exception {
        return CellarFacade.getInstance().getPublicCellars();
    }

    /**
     * Get all the cellars of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars of the user.
     */
    public List<Cellar> getCellarsFromUser(ObjectId userId) throws Exception {
        return CellarFacade.getInstance().getCellarsFromUser(userId);
    }

    /**
     * Get all the cellars where the user is a reader.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a reader.
     */
    public List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws Exception {
        return CellarFacade.getInstance().getReadOnlyCellarsFromUser(userId);
    }

    /**
     * Get cellars where the user is a manager.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a manager.
     */
    public List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws Exception {
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
}