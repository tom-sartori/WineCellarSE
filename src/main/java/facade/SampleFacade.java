package facade;

import logic.controller.sample.SampleController;
import org.bson.types.ObjectId;
import persistence.entity.sample.Sample;

import java.util.List;

/**
 * Specific facade for Samples.
 */
class SampleFacade {
    /// TODO : Be sure that you need every methods which are generated in this class. If not, remove them.
    /// TODO : After that, update Facade and FacadeInterface with those methods.

    /**
     * Singleton instance.
     */
    private static SampleFacade instance;

    private SampleFacade() { }

    /**
     * Get the singleton instance of the sample facade.
     *
     * @return The singleton instance.
     */
    public static SampleFacade getInstance() {
        if (instance == null) {
            instance = new SampleFacade();
        }
        return instance;
    }

    /**
     * Insert a sample.
     *
     * @param sample The sample to insert.
     * @return The id of the inserted sample.
     */
    protected ObjectId insertOneSample(Sample sample) {
        return SampleController.getInstance().insertOne(sample);
    }

    /**
     * Get all samples.
     *
     * @return A list of samples.
     */
    protected List<Sample> getSampleList() {
        return SampleController.getInstance().findAll();
    }

    /**
     * Get a sample by its id.
     *
     * @param id The id of the sample.
     * @return The sample or null if not found.
     */
    protected Sample getOneSample(ObjectId id) {
        return SampleController.getInstance().findOne(id);
    }

    /**
     * Update a sample.
     *
     * @param id The id of the sample to update.
     * @param sample The new sample.
     * @return true if the sample has been updated, false otherwise.
     */
    protected boolean updateOneSample(ObjectId id, Sample sample) {
        return SampleController.getInstance().updateOne(id, sample);
    }

    /**
     * Delete a sample.
     *
     * @param id The id of the sample to delete.
     * @return true if the sample has been deleted, false otherwise.
     */
    protected boolean deleteOneSample(ObjectId id) {
        return SampleController.getInstance().deleteOne(id);
    }
}
