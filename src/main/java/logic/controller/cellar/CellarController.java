package logic.controller.cellar;

import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import persistence.dao.Dao;
import persistence.dao.cellar.CellarDAO;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.List;

public class CellarController extends AbstractController<Cellar> {

    private static CellarController instance;

    private CellarController() { }

    public static CellarController getInstance() {
        if(instance == null){
            instance = new CellarController();
        }
        return instance;
    }

    /**
     * Add a cellar reader.
     *
     * @param user The user to add to readers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addCellarReader(ObjectId user, ObjectId cellar){
        return CellarDAO.getInstance().addCellarReader(user, cellar);
    }

    /**
     * Remove a cellar reader.
     *
     * @param user The user to remove from readers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId removeCellarReader(ObjectId user, ObjectId cellar){
        return CellarDAO.getInstance().removeCellarReader(user, cellar);
    }

    /**
     * Add a cellar manager.
     *
     * @param user The user to add to managers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addCellarManager(ObjectId user, ObjectId cellar){
        return CellarDAO.getInstance().addCellarManager(user, cellar);
    }

    /**
     * Remove a cellar manager.
     *
     * @param user The user to remove from managers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId removeCellarManager(ObjectId user, ObjectId cellar){
        return CellarDAO.getInstance().removeCellarManager(user, cellar);
    }

    /**
     * Add a wall to a cellar.
     *
     * @param cellar The cellar to add the wall to.
     * @param wall The wall to add.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addWall(Wall wall, ObjectId cellar){
        return CellarDAO.getInstance().addWall(wall, cellar);
    }

    /**
     * Remove a wall from a cellar.
     *
     * @param cellar The cellar to remove the wall from.
     * @param wall The wall to remove.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId removeWall(Wall wall, ObjectId cellar){
        return CellarDAO.getInstance().removeWall(wall, cellar);
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
        return CellarDAO.getInstance().addBottle(wall, cellar, bottle, emplacementBottle);
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
        return CellarDAO.getInstance().removeBottle(wall, cellar, bottle, emplacementBottle);
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
    public ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle){
        return CellarDAO.getInstance().addEmplacement(cellar, wall, emplacementBottle);
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
    public ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle){
        return CellarDAO.getInstance().removeEmplacement(cellar, wall, emplacementBottle);
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
    public ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity){
        return CellarDAO.getInstance().increaseBottleQuantity(cellar, wall, emplacementBottle, bottleQuantity);
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
    public ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity){
        return CellarDAO.getInstance().decreaseBottleQuantity(cellar, wall, emplacementBottle, bottleQuantity);
    }

    /**
     * Get all public cellars.
     *
     * @return A list of all public cellars.
     */
    public List<Cellar> getPublicCellars() throws Exception {
        return CellarDAO.getInstance().getPublicCellars();
    }

    /**
     * Get all the cellars of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars of the user.
     */
    public List<Cellar> getCellarsFromUser(ObjectId userId) throws Exception {
        return CellarDAO.getInstance().getCellarsFromUser(userId);
    }

    /**
     * Get all the cellars where the user is a reader.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a reader.
     */
    public List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws Exception {
        return CellarDAO.getInstance().getReadOnlyCellarsFromUser(userId);
    }

    /**
     * Get cellars where the user is a manager.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a manager.
     */
    public List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws Exception {
        return CellarDAO.getInstance().getCellarsWhereUserIsManager(userId);
    }

    @Override
    protected Dao<Cellar> getDao() {
        return CellarDAO.getInstance();
    }

}
