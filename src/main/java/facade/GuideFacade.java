package facade;

import logic.controller.guide.GuideController;
import org.bson.types.ObjectId;
import persistence.entity.guide.Guide;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Specific facade for Guides.
 */
class GuideFacade {

    /**
     * Singleton instance.
     */
    private static GuideFacade instance;

    private GuideFacade() { }

    /**
     * Get the singleton instance of the guide facade.
     *
     * @return The singleton instance.
     */
    public static GuideFacade getInstance() {
        if (instance == null) {
            instance = new GuideFacade();
        }
        return instance;
    }

    /**
     * Insert a guide.
     *
     * @param guide The guide to insert.
     * @return The id of the inserted guide.
     */
    protected ObjectId insertOneGuide(Guide guide) {
        return GuideController.getInstance().insertOne(guide);
    }

    /**
     * Get all guides.
     *
     * @return A list of guides.
     */
    protected List<Guide> getGuideList() {
        return GuideController.getInstance().findAll();
    }

    /**
     * Get a guide by its id.
     *
     * @param id The id of the guide.
     * @return The guide or null if not found.
     */
    protected Guide getOneGuide(ObjectId id) {
        return GuideController.getInstance().findOne(id);
    }

    /**
     * Update a guide.
     *
     * @param id The id of the guide to update.
     * @param guide The new guide.
     * @return true if the guide has been updated, false otherwise.
     */
    protected boolean updateOneGuide(ObjectId id, Guide guide) {
        return GuideController.getInstance().updateOne(id, guide);
    }

    /**
     * Delete a guide.
     *
     * @param id The id of the guide to delete.
     * @return true if the guide has been deleted, false otherwise.
     */
    protected boolean deleteOneGuide(ObjectId id) {
        return GuideController.getInstance().deleteOne(id);
    }


}
