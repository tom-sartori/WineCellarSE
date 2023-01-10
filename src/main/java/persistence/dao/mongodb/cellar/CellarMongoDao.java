package persistence.dao.mongodb.cellar;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import exception.BadArgumentsException;
import exception.NotFoundException;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

/**
 * Cellar DAO.
 */
public class CellarMongoDao extends AbstractMongoDao<Cellar> {

    private static CellarMongoDao instance;

    /**
     * Singleton instance.
     */
    private CellarMongoDao() { }

    /**
     * Get the singleton instance of the DAO.
     *
     * @return The singleton instance.
     */
    public static CellarMongoDao getInstance() {
        if(instance == null) {
            instance = new CellarMongoDao();
        }
        return instance;
    }

    /**
     * Get all public cellars.
     *
     * @return A list of all public cellars if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getPublicCellars() throws NotFoundException {
        BsonDocument filter = new BsonDocument();
        filter.append("public", BsonBoolean.TRUE);
        return findAllWithFilter(filter);
    }

    /**
     * Get all the cellars of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars of the user if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getCellarsFromUser(ObjectId userId) throws NotFoundException {
        BsonDocument filter = new BsonDocument();
        filter.append("ownerRef", new org.bson.BsonObjectId(userId));
        return findAllWithFilter(filter);
    }

    /**
     * Get all the cellars where the user is a reader.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a reader if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws NotFoundException {
        BsonDocument filter = new BsonDocument();
        filter.append("readers", new org.bson.BsonObjectId(userId));
        return findAllWithFilter(filter);
    }

    /**
     * Get cellars where the user is a manager.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a manager if there are any, otherwise throws a NotFoundException.
     */
    public List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws NotFoundException {
        BsonDocument filter = new BsonDocument();
        filter.append("managers", new org.bson.BsonObjectId(userId));
        return findAllWithFilter(filter);
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
        return addOrRemoveFromSet(cellar, user, "managers", true);
    }

    /**
     * Remove a cellar manager.
     *
     * @param user The user to remove from managers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId removeCellarManager(ObjectId user, ObjectId cellar) throws BadArgumentsException {
        return addOrRemoveFromSet(cellar, user, "managers", false);
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
        return addOrRemoveFromSet(cellar, user, "readers", true);
    }

    /**
     * Remove a cellar reader.
     *
     * @param user The user to remove from readers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId removeCellarReader(ObjectId user, ObjectId cellar) throws BadArgumentsException {
        return addOrRemoveFromSet(cellar, user, "readers", false);
    }

    /**
     * Add a wall to a cellar.
     *
     * @param cellar The cellar to add the wall to.
     * @param wall The wall to add.
     *
     * @return The id of the updated cellar if the update was successful, otherwise throws a BadArgumentsException.
     */
    public ObjectId addWall(Wall wall, ObjectId cellar) throws BadArgumentsException {
        return addOrRemoveFromSet(cellar, wall, "walls", true);
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
        return addOrRemoveFromSet(cellar, wall, "walls", false);
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
        int indexOfWall = cellar.getWalls().indexOf(wall);
        if (indexOfWall == -1){
            throw new BadArgumentsException("Le mur indiqué n'est pas dans la cave");
        }
        return addOrRemoveFromSet(cellar.getId(), emplacementBottle, "walls." + indexOfWall + ".emplacementBottleMap", true);
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
    public ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) throws BadArgumentsException {
        int indexOfWall = cellar.getWalls().indexOf(wall);
        return addOrRemoveFromSet(cellar.getId(), emplacementBottle, "walls." + indexOfWall + ".emplacementBottleMap", false);
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
        int indexOfWall = cellar.getWalls().indexOf(wall);
        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);
        int indexOfBottle = emplacementBottle.getBottleList().indexOf(bottleQuantity);

        bottleQuantity.setQuantity( bottleQuantity.getQuantity() + 1 );
        boolean hasBeenModified = super.updateOne(cellar.getId(), Updates.set("walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList." + indexOfBottle + ".quantity", bottleQuantity.getQuantity()));

        if(hasBeenModified){
            return cellar.getId();
        }
        else {
            throw new BadArgumentsException("Mauvais arguments");
        }
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
    public ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) throws BadArgumentsException {
        int indexOfWall = cellar.getWalls().indexOf(wall);
        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);
        int indexOfBottle = emplacementBottle.getBottleList().indexOf(bottleQuantity);

        bottleQuantity.setQuantity(bottleQuantity.getQuantity() - 1);
        boolean hasBeenModified = super.updateOne(cellar.getId(), Updates.set("walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList." + indexOfBottle + ".quantity", bottleQuantity.getQuantity()));

        if(hasBeenModified){
            return cellar.getId();
        }
        else {
            throw new BadArgumentsException("Mauvais arguments");
        }
    }

    /**
     * @return the name of the collection in the database
     */
    @Override
    protected String getCollectionName() {
        return CollectionNames.CELLAR;
    }

    /**
     * @return the class of the entity
     */
    @Override
    protected Class<Cellar> getEntityClass() {
        return Cellar.class;
    }

    /**
     *
     * @return the list of fields that can be used to update the collection
     */
    @Override
    protected Bson getSetOnUpdate(Cellar entity) {
        List<Bson> updateList = new ArrayList<>();

        updateList.add(Updates.set("name", entity.getName()));
        updateList.add(Updates.set("public", entity.isPublic()));
        updateList.add(Updates.set("readers", entity.getReaders()));
        updateList.add(Updates.set("managers", entity.getManagers()));
        updateList.add(Updates.set("ownerRef", entity.getOwnerRef()));
        updateList.add(Updates.set("walls", entity.getWalls()));

        return combine(updateList);
    }
}
