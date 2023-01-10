package logic.controller.bottle;

import exception.BadArgumentsException;
import exception.NotFoundException;
import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.bottle.BottleMongoDao;
import persistence.dao.mongodb.cellar.CellarMongoDao;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.List;

/**
 * BottleController class extending Controller class parametrized with Bottle class.
 */
public class BottleController extends AbstractController<Bottle> {

    /**
     * Instance of BottleController to ensure Singleton design pattern.
     */
    private static BottleController instance;

    /**
     * Private constructor for BottleController to ensure Singleton design pattern.
     */
    private BottleController() { }

    /**
     * @return the instance of BottleController to ensure Singleton design pattern.
     */
    public static BottleController getInstance() {
        if(instance == null){
            instance = new BottleController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (BottleDao).
     */
    @Override
    protected BottleMongoDao getDao() {
        return BottleMongoDao.getInstance();
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

        if (wall == null || cellar == null || bottle == null || emplacementBottle == null) {
            throw new BadArgumentsException("One or more arguments are null.");
        }

        verifyArguments(wall, cellar, emplacementBottle);

        return getDao().insertBottle(wall, cellar, bottle, emplacementBottle);
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

        if (cellarId == null) {
            throw new NotFoundException("Cellar id is null.");
        }

        Cellar cellar = CellarMongoDao.getInstance().findOne(cellarId);

        if (cellar == null) {
            throw new NotFoundException("Cellar not found.");
        }

        return getDao().getBottlesFromCellar(cellarId);
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

        if (wall == null || cellar == null || bottle == null || emplacementBottle == null || updatedBottle == null) {
            throw new BadArgumentsException("One or more arguments are null.");
        }

        verifyArguments(wall, cellar, emplacementBottle);

        return getDao().updateBottle(wall, cellar, bottle, emplacementBottle, updatedBottle);
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

        if (wall == null || cellar == null || bottle == null || emplacementBottle == null) {
            throw new BadArgumentsException("One or more arguments are null.");
        }

        verifyArguments(wall, cellar, emplacementBottle);

        return getDao().deleteBottle(wall, cellar, bottle, emplacementBottle);
    }

    /**
     * Verify if the arguments are valid.
     *
     * @param wall The wall to verify.
     * @param cellar The cellar to verify.
     * @param emplacementBottle The emplacement to verify.
     *
     * @throws BadArgumentsException If the arguments are not valid.
     * @throws NotFoundException If the cellar or the wall does not exist in the database or if the wall or emplacement are not in the cellar.
     */
    private void verifyArguments(Wall wall, Cellar cellar, EmplacementBottle emplacementBottle) throws BadArgumentsException {
        ObjectId cellarId = cellar.getId();

        if (cellarId == null) {
            throw new BadArgumentsException("The cellar id is null.");
        }

        Cellar one = CellarMongoDao.getInstance().findOne(cellarId);

        if (one == null) {
            throw new BadArgumentsException("The cellar does not exist.");
        }

        int indexOfWall = cellar.getWalls().indexOf(wall);

        if (indexOfWall == -1) {
            throw new NotFoundException("The wall does not exist in this cellar.");
        }

        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);

        if (indexOfEmplacement == -1) {
            throw new NotFoundException("The emplacement does not exist in this cellar.");
        }
    }

}
