package facade;

import logic.controller.advertizing.AdvertizingController;
import org.bson.types.ObjectId;
import persistence.entity.advertizing.Advertizing;

import java.util.List;

/**
 * Specific facade for Advertizings.
 */
class AdvertizingFacade {
    /// TODO : Be sure that you need every methods which are generated in this class. If not, remove them.
    /// TODO : After that, update Facade and FacadeInterface with those methods.

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
     * Get a advertizing by its id.
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
}
