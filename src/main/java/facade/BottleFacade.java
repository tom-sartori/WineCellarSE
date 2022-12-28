package facade;

import exception.BadArgumentsException;
import exception.NotFoundException;
import logic.controller.bottle.BottleController;
import org.bson.types.ObjectId;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.List;

/**
 * Specific facade for Bottles.
 */
class BottleFacade {

    /**
     * Singleton instance.
     */
    private static BottleFacade instance;

    private BottleFacade() { }

    /**
     * Get the singleton instance of the bottle facade.
     *
     * @return The singleton instance.
     */
    public static BottleFacade getInstance() {
        if (instance == null) {
            instance = new BottleFacade();
        }
        return instance;
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
        return BottleController.getInstance().insertBottle(wall, cellar, bottle, emplacementBottle);
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
        return BottleController.getInstance().getBottlesFromCellar(cellarId);
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
        return BottleController.getInstance().updateBottle(wall, cellar, bottle, emplacementBottle, updatedBottle);
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
        return BottleController.getInstance().deleteBottle(wall, cellar, bottle, emplacementBottle);
    }

}
