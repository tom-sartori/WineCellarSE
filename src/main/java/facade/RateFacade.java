package facade;

import exception.NotFoundException;
import logic.controller.cellar.CellarController;
import logic.controller.rate.RateController;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import persistence.entity.cellar.Cellar;
import persistence.entity.rate.Rate;

import java.util.List;

/**
 * Specific facade for Rates.
 */
class RateFacade {

    /**
     * Singleton instance.
     */
    private static RateFacade instance;

    private RateFacade() { }

    /**
     * Get the singleton instance of the rate facade.
     *
     * @return The singleton instance.
     */
    public static RateFacade getInstance() {
        if (instance == null) {
            instance = new RateFacade();
        }
        return instance;
    }

    /**
     * Insert a rate.
     *
     * @param rate The rate to insert.
     * @return The id of the inserted rate.
     */
    protected ObjectId insertOneRate(Rate rate) {
        return RateController.getInstance().insertOne(rate);
    }

    /**
     * Get all rates.
     *
     * @return A list of rates.
     */
    protected List<Rate> getRateList() {
        return RateController.getInstance().findAll();
    }

    /**
     * Get all the rates of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the rates of the user if there are any, otherwise throws a NotFoundException.
     */
    public List<Rate> getRateListFromUser(ObjectId userId) throws NotFoundException {
        return RateController.getInstance().getRateListFromUser(userId);
    }

    /**
     * Get all the rates of a subject.
     *
     * @param subjectId The id of the subject.
     *
     * @return A list of all the rates of the subject if there are any, otherwise throws a NotFoundException.
     */
    public List<Rate> getRateListFromSubject(ObjectId subjectId) throws NotFoundException {
        return RateController.getInstance().getRateListFromSubject(subjectId);
    }

    /**
     * Get a rate by its id.
     *
     * @param id The id of the rate.
     * @return The rate or null if not found.
     */
    protected Rate getOneRate(ObjectId id) {
        return RateController.getInstance().findOne(id);
    }

    /**
     * Update a rate.
     *
     * @param id The id of the rate to update.
     * @param rate The new rate.
     * @return true if the rate has been updated, false otherwise.
     */
    protected boolean updateOneRate(ObjectId id, Rate rate) {
        return RateController.getInstance().updateOne(id, rate);
    }

    /**
     * Delete a rate.
     *
     * @param id The id of the rate to delete.
     * @return true if the rate has been deleted, false otherwise.
     */
    protected boolean deleteOneRate(ObjectId id) {
        return RateController.getInstance().deleteOne(id);
    }
}
