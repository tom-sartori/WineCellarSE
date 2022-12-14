package persistence.dao.cellar;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import constant.CollectionNames;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.AbstractDao;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;

/**
 * Cellar DAO.
 */
public class CellarDAO extends AbstractDao<Cellar> {

    private static CellarDAO instance;

    /**
     * Singleton instance.
     */
    private CellarDAO() { }

    /**
     * Get the singleton instance of the DAO.
     *
     * @return The singleton instance.
     */
    public static CellarDAO getInstance() {
        if(instance == null) {
            instance = new CellarDAO();
        }
        return instance;
    }

    // Auxiliary methods

    /**
     *
     * @param cellar the cellar to insert or remove from.
     * @param o the object to insert or remove.
     * @param field the field to insert or remove from.
     * @param add true to add, false to remove.
     * @return the object id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addOrRemoveFromSet(ObjectId cellar, Object o, String field, boolean add) {
        try (MongoClient mongoClient = MongoClients.create(getClientSettings())) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Cellar> collection = database.getCollection(getCollectionName(), getEntityClass());

            Bson update = add ? Updates.push(field, o) : Updates.pull(field, o);
            UpdateResult id = collection.updateOne(eq("_id", cellar), update);
            if(id.getModifiedCount() == 0) {
                return null;
            }else {
                return cellar;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            /// TODO: handle exception.
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all public cellars.
     *
     * @return A list of all public cellars.
     */
    public List<Cellar> getPublicCellars() throws Exception {
        BsonDocument filter = new BsonDocument();
        filter.append("public", BsonBoolean.TRUE);
        return findAllWithFilter(filter);
    }

    /**
     * Get all the cellars of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars of the user.
     */
    public List<Cellar> getCellarsFromUser(ObjectId userId) throws Exception {
        BsonDocument filter = new BsonDocument();
        filter.append("ownerRef", new org.bson.BsonObjectId(userId));
        return findAllWithFilter(filter);
    }

    /**
     * Get all the cellars where the user is a reader.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a reader.
     */
    public List<Cellar> getReadOnlyCellarsFromUser(ObjectId userId) throws Exception {
        BsonDocument filter = new BsonDocument();
        filter.append("readers", new org.bson.BsonObjectId(userId));
        return findAllWithFilter(filter);
    }

    /**
     * Get cellars where the user is a manager.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the cellars where the user is a manager.
     */
    public List<Cellar> getCellarsWhereUserIsManager(ObjectId userId) throws Exception {
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
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addCellarManager(ObjectId user, ObjectId cellar) {
        return addOrRemoveFromSet(cellar, user, "managers", true);
    }

    /**
     * Remove a cellar manager.
     *
     * @param user The user to remove from managers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId removeCellarManager(ObjectId user, ObjectId cellar) {
        return addOrRemoveFromSet(cellar, user, "managers", false);
    }

    /**
     * Add a cellar reader.
     *
     * @param user The user to add to readers.
     * @param cellar The cellar to add the user to.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addCellarReader(ObjectId user, ObjectId cellar) {
        return addOrRemoveFromSet(cellar, user, "readers", true);
    }

    /**
     * Remove a cellar reader.
     *
     * @param user The user to remove from readers.
     * @param cellar The cellar to remove the user from.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId removeCellarReader(ObjectId user, ObjectId cellar) {
        return addOrRemoveFromSet(cellar, user, "readers", false);
    }

    /**
     * Add a wall to a cellar.
     *
     * @param cellar The cellar to add the wall to.
     * @param wall The wall to add.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId addWall(Wall wall, ObjectId cellar) {
        return addOrRemoveFromSet(cellar, wall, "walls", true);
    }

    /**
     * Remove a wall from a cellar.
     *
     * @param cellar The cellar to remove the wall from.
     * @param wall The wall to remove.
     *
     * @return The id of the updated cellar if the update was successful, null otherwise.
     */
    public ObjectId removeWall(Wall wall, ObjectId cellar) {
        return addOrRemoveFromSet(cellar, wall, "walls", false);
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
    public ObjectId addBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle) {
        BottleQuantity bottleQuantity = new BottleQuantity(bottle, 1);
        int indexOfWall = cellar.getWalls().indexOf(wall);
        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);
        return addOrRemoveFromSet(cellar.getId(), bottleQuantity, "walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList", true);
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
    public ObjectId removeBottle(Wall wall, Cellar cellar, Bottle bottle, EmplacementBottle emplacementBottle) {
        int indexOfWall = cellar.getWalls().indexOf(wall);
        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);

        for (int i = 0; i < emplacementBottle.getBottleList().size(); i++) {
            if(emplacementBottle.getBottleList().get(i).getBottle().equals(bottle)){
                return addOrRemoveFromSet(cellar.getId(), emplacementBottle.getBottleList().get(i), "walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList", false);
            }
        }
        return null;
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
    public ObjectId addEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) {
        int indexOfWall = cellar.getWalls().indexOf(wall);
        return addOrRemoveFromSet(cellar.getId(), emplacementBottle, "walls." + indexOfWall + ".emplacementBottleMap", true);
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
    public ObjectId removeEmplacement(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle) {
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
     * @return The id of the updated cellar if the bottle was found and updated, null otherwise.
     */
    public ObjectId increaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) {
        int indexOfWall = cellar.getWalls().indexOf(wall);
        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);
        int indexOfBottle = emplacementBottle.getBottleList().indexOf(bottleQuantity);

        bottleQuantity.setQuantity( bottleQuantity.getQuantity() + 1 );

        boolean hasBeenModified = super.updateOne(cellar.getId(), Updates.set("walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList." + indexOfBottle + ".quantity", bottleQuantity.getQuantity()));
        if(hasBeenModified){
            return cellar.getId();
        }
        else {
            return null;
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
     * @return The id of the updated cellar if the quantity is greater than 0 and the field has been updated, null otherwise.
     */
    public ObjectId decreaseBottleQuantity(Cellar cellar, Wall wall, EmplacementBottle emplacementBottle, BottleQuantity bottleQuantity) {
        int indexOfWall = cellar.getWalls().indexOf(wall);
        int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);
        int indexOfBottle = emplacementBottle.getBottleList().indexOf(bottleQuantity);

        if (bottleQuantity.getQuantity() > 0) {
            bottleQuantity.setQuantity(bottleQuantity.getQuantity() - 1);
            boolean hasBeenModified = super.updateOne(cellar.getId(), Updates.set("walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList." + indexOfBottle + ".quantity", bottleQuantity.getQuantity()));
            if(hasBeenModified){
                return cellar.getId();
            }
        }
        else{
            return removeBottle(wall, cellar, bottleQuantity.getBottle(), emplacementBottle);
        }
        return null;
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
