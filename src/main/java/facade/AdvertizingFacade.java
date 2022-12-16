package facade;

import logic.controller.advertizing.AdvertizingController;
import org.bson.types.ObjectId;
import persistence.entity.advertizing.Advertizing;

import java.util.Date;
import java.util.List;

/**
 * Specific facade for Advertizings.
 */
class AdvertizingFacade {

    /**
     * Singleton instance.
     */
    private static AdvertizingFacade instance;

    private AdvertizingFacade() { }

    /**
     * Get the singleton instance of the advertizing facade.
     *
     * @return The singleton instance.
     */
    public static AdvertizingFacade getInstance() {
        if (instance == null) {
            instance = new AdvertizingFacade();
        }
        return instance;
    }

    /**
     * Insert a advertizing.
     *
     * @param advertizing The advertizing to insert.
     * @return The id of the inserted advertizing.
     */
    protected ObjectId insertOneAdvertizing(Advertizing advertizing) {
        return AdvertizingController.getInstance().insertOne(advertizing);
    }

    /**
     * Get all advertizings.
     *
     * @return A list of advertizings.
     */
    protected List<Advertizing> getAdvertizingList() {
        return AdvertizingController.getInstance().findAll();
    }

    /**
     * Get an advertizing by its id.
     *
     * @param id The id of the advertizing.
     * @return The advertizing or null if not found.
     */
    protected Advertizing getOneAdvertizing(ObjectId id) {
        return AdvertizingController.getInstance().findOne(id);
    }

    /**
     * Update a advertizing.
     *
     * @param id The id of the advertizing to update.
     * @param advertizing The new advertizing.
     * @return true if the advertizing has been updated, false otherwise.
     */
    protected boolean updateOneAdvertizing(ObjectId id, Advertizing advertizing) {
        return AdvertizingController.getInstance().updateOne(id, advertizing);
    }

    /**
     * Delete a advertizing.
     *
     * @param id The id of the advertizing to delete.
     * @return true if the advertizing has been deleted, false otherwise.
     */
    protected boolean deleteOneAdvertizing(ObjectId id) {
        return AdvertizingController.getInstance().deleteOne(id);
    }

    /**
     * Renew an advertizing.
     *
     * @param id The id of the advertizing to renew.
     * @param endDate The new end date of the advertizing.
     * @return true if the advertizing has been renewed, false otherwise.
     */
    protected boolean renewOneAdvertizing(ObjectId id, Date endDate) {
        return AdvertizingController.getInstance().renewOneAdvertizing(id, endDate);
    }

    /**
     * Pay for an advertizing.
     *
     * @param id The id of the advertizing to pay for.
     * @return true if the advertizing has been paid, false otherwise.
     */
    protected boolean payOneAdvertizing(ObjectId id) {
        return AdvertizingController.getInstance().payOneAdvertizing(id);
    }

    /**
     * add a view to an advertizing.
     *
     * @param id The id of the advertizing.
     * @return true if the view was added to the advertizing, false otherwise.
     */
    protected boolean addView(ObjectId id) {
        return AdvertizingController.getInstance().addView(id);
    }

    /**
     * validate an advertizing.
     *
     * @param id The id of the advertizing to validate.
     * @return true if the advertizing has been validated, false otherwise.
     */
    protected boolean validateAdvertizing(ObjectId id) {
        return AdvertizingController.getInstance().validateAdvertizing(id);
    }
}
