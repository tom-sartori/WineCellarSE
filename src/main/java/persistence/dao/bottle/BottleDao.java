package persistence.dao.bottle;

import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import exception.BadArgumentsException;
import exception.NotFoundException;
import logic.controller.cellar.CellarController;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.AbstractDao;
import persistence.dao.cellar.CellarDAO;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.BottleQuantity;
import persistence.entity.cellar.Cellar;
import persistence.entity.cellar.EmplacementBottle;
import persistence.entity.cellar.Wall;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class BottleDao extends AbstractDao<Bottle> {

	private static BottleDao instance;

	private BottleDao() { }

	public static BottleDao getInstance() {
		if (instance == null) {
			instance = new BottleDao();
		}
		return instance;
	}

	@Override
	public ObjectId insertOne(Bottle entity) throws MongoWriteException, UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not available for BottleDao.");
	}

	@Override
	public ArrayList<Bottle> findAll() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not available for BottleDao.");
	}

	/**
	 * Find a bottle by id.
	 *
	 * @param id The id of the bottle.
	 * @return The bottle if found, otherwise throws a NotFoundException.
	 */
	@Override
	public Bottle findOne(ObjectId id) {

		List<Cellar> all = CellarController.getInstance().findAll();

		for (Cellar cellar : all) {
			for (Wall wall : cellar.getWalls()) {
				for (EmplacementBottle emplacementBottle : wall.getEmplacementBottleMap()) {
					for (BottleQuantity bottleQuantity : emplacementBottle.getBottleList()) {
						// TODO remove first if when the datatbase is updated.
						if (bottleQuantity.getBottle().getId() != null && bottleQuantity.getBottle().getId().equals(id)) {
							return bottleQuantity.getBottle();
						}
					}
				}
			}
		}

		throw new NotFoundException("Bottle not found.");
	}

	@Override
	public boolean updateOne(ObjectId id, Bottle newEntity) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not available for BottleDao.");
	}

	@Override
	public boolean deleteOne(ObjectId id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not available for BottleDao.");
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
		bottle.handleOnCreate();
		BottleQuantity bottleQuantity = new BottleQuantity(bottle, 1);
		int indexOfWall = cellar.getWalls().indexOf(wall);
		int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);
		return addOrRemoveFromSet(cellar.getId(), bottleQuantity, "walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList", true);
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
		List<Bottle> bottles = new ArrayList<>();

		// Get the cellar
		Cellar cellar = CellarDAO.getInstance().findOne(cellarId);

		// Get all bottles from the cellar
		for (Wall wall : cellar.getWalls()) {
			for (EmplacementBottle emplacementBottle : wall.getEmplacementBottleMap()) {
				for (BottleQuantity bottleQuantity : emplacementBottle.getBottleList()) {
					bottles.add(bottleQuantity.getBottle());
				}
			}
		}

		// Check if there is at least one bottle
		if (bottles.size() > 0){
			return bottles;
		}
		else {
			throw new NotFoundException("No bottles found in cellar " + cellarId);
		}
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
		int indexOfWall = cellar.getWalls().indexOf(wall);
		int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);

		int quantity = 0;
		int indexOfBottle = -1;
		// Looking for the bottle in the list of bottles to remove it.
		for (int i = 0; i < emplacementBottle.getBottleList().size(); i++) {
			if(emplacementBottle.getBottleList().get(i).getBottle().equals(bottle)){
				quantity = emplacementBottle.getBottleList().get(i).getQuantity();
				indexOfBottle = i;
			}
		}

		// Remove the bottle from the list
		addOrRemoveFromSet(cellar.getId(), emplacementBottle.getBottleList().get(indexOfBottle), "walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList", false);

		// Add the updated bottle to the list
		BottleQuantity bottleQuantity = new BottleQuantity(updatedBottle, quantity);
		ObjectId objectId = addOrRemoveFromSet(cellar.getId(), bottleQuantity, "walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList", true);

		if (objectId != null) {
			return objectId;
		}
		else {
			throw new BadArgumentsException("The bottle " + bottle.getBottleName() + " was not found in the cellar " + cellar.getId());
		}
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
		int indexOfWall = cellar.getWalls().indexOf(wall);
		int indexOfEmplacement = wall.getEmplacementBottleMap().indexOf(emplacementBottle);

		// Looking for the bottle in the list of bottles to remove it.
		for (int i = 0; i < emplacementBottle.getBottleList().size(); i++) {
			if(emplacementBottle.getBottleList().get(i).getBottle().equals(bottle)){
				return addOrRemoveFromSet(cellar.getId(), emplacementBottle.getBottleList().get(i), "walls." + indexOfWall + ".emplacementBottleMap." + indexOfEmplacement + ".bottleList", false);
			}
		}
		return null;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.CELLAR;
	}

	@Override
	protected Class<Bottle> getEntityClass() {
		return Bottle.class;
	}

	@Override
	protected Bson getSetOnUpdate(Bottle entity) {

		List<Bson> updateList = new ArrayList<>();

		// needed attributes
		updateList.add(Updates.set("appellation", entity.getAppellation()));
		updateList.add(Updates.set("producer", entity.getProducer()));
		updateList.add(Updates.set("alcoholPercentage", entity.getAlcoholPercentage()));
		updateList.add(Updates.set("bottleSize", entity.getBottleSize()));
		updateList.add(Updates.set("sizeUnit", entity.getSizeUnit()));
		updateList.add(Updates.set("category", entity.getCategory()));
		updateList.add(Updates.set("grapeList", entity.getGrapeList()));

		// Nullable attributes

		if (entity.getBottleName() != null) {
			updateList.add(Updates.set("bottleName", entity.getBottleName()));
		}

		if (entity.getVintage() != 0) {
			updateList.add(Updates.set("vintage", entity.getVintage()));
		}

		if (entity.getBottleImage() != null) {
			updateList.add(Updates.set("bottleImage", entity.getBottleImage()));
		}

		if (entity.getPrice() != 0) {
			updateList.add(Updates.set("price", entity.getPrice()));
		}

		return combine(updateList);
	}
}
