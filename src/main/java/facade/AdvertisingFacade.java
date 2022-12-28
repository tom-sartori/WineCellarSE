package facade;

import logic.controller.advertising.AdvertisingController;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;

import java.util.Date;
import java.util.List;

/**
 * Specific facade for Advertisings.
 */
class AdvertisingFacade {

    /**
     * Singleton instance.
     */
    private static AdvertisingFacade instance;

    private AdvertisingFacade() { }

    /**
     * Get the singleton instance of the advertising facade.
     *
     * @return The singleton instance.
     */
    public static AdvertisingFacade getInstance() {
        if (instance == null) {
            instance = new AdvertisingFacade();
        }
        return instance;
    }

    /**
     * Insert a advertising.
     *
     * @param advertising The advertising to insert.
     * @return The id of the inserted advertising.
     */
    protected ObjectId insertOneAdvertising(Advertising advertising) {
        return AdvertisingController.getInstance().insertOne(advertising);
    }

    /**
     * Get all advertisings.
     *
     * @return A list of advertisings.
     */
    protected List<Advertising> getAdvertisingList() {
        return AdvertisingController.getInstance().findAll();
    }

    /**
     * Get an advertising by its id.
     *
     * @param id The id of the advertising.
     * @return The advertising or null if not found.
     */
    protected Advertising getOneAdvertising(ObjectId id) {
        return AdvertisingController.getInstance().findOne(id);
    }

    /**
     * Update a advertising.
     *
     * @param id The id of the advertising to update.
     * @param advertising The new advertising.
     * @return true if the advertising has been updated, false otherwise.
     */
    protected boolean updateOneAdvertising(ObjectId id, Advertising advertising) {
        return AdvertisingController.getInstance().updateOne(id, advertising);
    }

    /**
     * Delete a advertising.
     *
     * @param id The id of the advertising to delete.
     * @return true if the advertising has been deleted, false otherwise.
     */
    protected boolean deleteOneAdvertising(ObjectId id) {
        return AdvertisingController.getInstance().deleteOne(id);
    }

    /**
     * Renew an advertising.
     *
     * @param id The id of the advertising to renew.
     * @param endDate The new end date of the advertising.
     * @return true if the advertising has been renewed, false otherwise.
     */
    protected boolean renewOneAdvertising(ObjectId id, Date endDate) {
        return AdvertisingController.getInstance().renewOneAdvertising(id, endDate);
    }

    /**
     * Pay for an advertising.
     *
     * @param id The id of the advertising to pay for.
     * @return true if the advertising has been paid, false otherwise.
     */
    protected boolean payOneAdvertising(ObjectId id) {
        return AdvertisingController.getInstance().payOneAdvertising(id);
    }

    /**
     * add a view to an advertising.
     *
     * @param id The id of the advertising.
     * @return true if the view was added to the advertising, false otherwise.
     */
    protected boolean addView(ObjectId id) {
        return AdvertisingController.getInstance().addView(id);
    }

    /**
     * validate an advertising.
     *
     * @param id The id of the advertising to validate.
     * @return true if the advertising has been validated, false otherwise.
     */
    protected boolean validateAdvertising(ObjectId id) {
        return AdvertisingController.getInstance().validateAdvertising(id);
    }

    /**
     * Get advertising by their company id.
     *
     * @param company The id of the advertised company.
     * @return A list of advertisings.
     */
    protected List<Advertising> getAdvertisingByCompany(ObjectId company) {
        return AdvertisingController.getInstance().findAllByCompany(company);
    }
}
