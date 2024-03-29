package facade;

import exception.BadArgumentsException;
import exception.NotFoundException;
import logic.controller.cellar.CellarController;
import org.bson.types.ObjectId;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.List;

public class CellarFacade {

    private static CellarFacade instance;

    private CellarFacade() { }

    /**
     * Get the singleton instance of the facade.
     *
     * @return The singleton instance.
     */
    public static CellarFacade getInstance() {
        if(instance == null) {
            instance = new CellarFacade();
        }
        return instance;
    }

    /**
     * Insert a cellar.
     *
     * @param cellar The cellar to insert.
     * @return The id of the inserted cellar.
     */
    protected ObjectId insertOneCellar(Cellar cellar) {
        return CellarController.getInstance().insertOne(cellar);
    }

    /**
     * Get all Cellars.
     *
     * @return A list of cellars.
     */
    protected List<Cellar> getCellarList() {
        return CellarController.getInstance().findAll();
    }

    /**
     * Get a cellar by its id.
     *
     * @param id The id of the cellar.
     * @return The cellar or null if not found.
     */
    protected Cellar getOneCellar(ObjectId id) {
        return CellarController.getInstance().findOne(id);
    }

    /**
     * Update a cellar.
     *
     * @param id The id of the cellar to update.
     * @param cellar The new cellar.
     * @return true if the cellar has been updated, false otherwise.
     */
    protected boolean updateOneCellar(ObjectId id, Cellar cellar) {
        return CellarController.getInstance().updateOne(id, cellar);
    }

    /**
     * Delete a cellar.
     *
     * @param id The id of the cellar to delete.
     * @return true if the cellar has been deleted, false otherwise.
     */
    protected boolean deleteOneCellar(ObjectId id) {
        return CellarController.getInstance().deleteOne(id);
    }

    /**
     * Add a cellar reader.
     *
     * @param user The user to add to readers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId addCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException {
        return CellarController.getInstance().addCellarReader(user,cellar);
    }

    /**
     * Remove a cellar reader.
     *
     * @param user The user to remove from readers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId removeCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException{
        return CellarController.getInstance().removeCellarReader(user,cellar);
    }

    /**
     * Add a cellar manager.
     *
     * @param user The user to add to managers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId addCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException {
        return CellarController.getInstance().addCellarManager(user,cellar);
    }

    /**
     * Remove a cellar manager.
     *
     * @param user The user to remove from managers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId removeCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException{
        return CellarController.getInstance().removeCellarManager(user,cellar);
    }

    /**
     * Add a wall to a cellar.
     *
     * @param cellar The cellar to add the wall to.
     * @param wall The wall to add.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId addWall(Wall wall, ObjectId cellar) throws BadArgumentsException{
        return CellarController.getInstance().addWall(wall,cellar);
    }

    /**
     * Remove a wall from a cellar.
     *
     * @param cellar The cellar to remove the wall from.
     * @param wall The wall to remove.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId removeWall(Wall wall, ObjectId cellar) throws BadArgumentsException {
        return CellarController.getInstance().removeWall(wall,cellar);
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
    public ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException {
        return CellarController.getInstance().addEmplacement(cellar,wall,emplacementBottle);
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
    public ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException{
        return CellarController.getInstance().removeEmplacement(cellar,wall,emplacementBottle);
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
    public ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException {
        return CellarController.getInstance().increaseBottleQuantity(cellar,wall,emplacementBottle,bottleQuantity);
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
    public ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException{
        return CellarController.getInstance().decreaseBottleQuantity(cellar,wall,emplacementBottle,bottleQuantity);
    }

    /**
     * Get all public cellars.
     *
     * @return A list of all public cellars if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getPublicCellars() throws NotFoundException {
        return CellarController.getInstance().getPublicCellars();
    }

    /**
     * Get all the cellars of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars of the user if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getCellarsFromUser(ObjectId userId) throws NotFoundException {
        return CellarController.getInstance().getCellarsFromUser(userId);
    }

    /**
     * Get all the cellars where the user is a reader.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a reader if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws NotFoundException {
        return CellarController.getInstance().getReadOnlyCellarsFromUser(userId);
    }

    /**
     * Get cellars where the user is a manager.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a manager if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws NotFoundException {
        return CellarController.getInstance().getCellarsWhereUserIsManager(userId);
    }
}
