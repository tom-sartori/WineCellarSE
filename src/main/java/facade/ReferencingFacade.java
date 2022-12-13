package facade;

import logic.controller.referencing.ReferencingController;
import org.bson.types.ObjectId;
import persistence.entity.referencing.Referencing;

import java.util.List;

/**
 * Specific facade for Referencings.
 */
class ReferencingFacade {

    /**
     * Singleton instance.
     */
    private static ReferencingFacade instance;

    private ReferencingFacade() { }

    /**
     * Get the singleton instance of the referencing facade.
     *
     * @return The singleton instance.
     */
    public static ReferencingFacade getInstance() {
        if (instance == null) {
            instance = new ReferencingFacade();
        }
        return instance;
    }

    /**
     * Insert a referencing.
     *
     * @param referencing The referencing to insert.
     * @return The id of the inserted referencing.
     */
    protected ObjectId insertOneReferencing(Referencing referencing) {
        return ReferencingController.getInstance().insertOne(referencing);
    }

    /**
     * Get all referencings.
     *
     * @return A list of referencings.
     */
    protected List<Referencing> getReferencingList() {
        return ReferencingController.getInstance().findAll();
    }

    /**
     * Get a referencing by its id.
     *
     * @param id The id of the referencing.
     * @return The referencing or null if not found.
     */
    protected Referencing getOneReferencing(ObjectId id) {
        return ReferencingController.getInstance().findOne(id);
    }

    /**
     * Get referencings by their importanceLevel.
     *
     * @param importanceLevel The level of importance of the searched referencings.
     * @return A list of referencings.
     */
    protected List<Referencing> getReferencingByLevel(int importanceLevel) {
        return ReferencingController.getInstance().findAllByLevel(importanceLevel);
    }

    /**
     * Update a referencing.
     *
     * @param id The id of the referencing to update.
     * @param referencing The new referencing.
     * @return true if the referencing has been updated, false otherwise.
     */
    protected boolean updateOneReferencing(ObjectId id, Referencing referencing) {
        return ReferencingController.getInstance().updateOne(id, referencing);
    }

    /**
     * Delete a referencing.
     *
     * @param id The id of the referencing to delete.
     * @return true if the referencing has been deleted, false otherwise.
     */
    protected boolean deleteOneReferencing(ObjectId id) {
        return ReferencingController.getInstance().deleteOne(id);
    }
}
