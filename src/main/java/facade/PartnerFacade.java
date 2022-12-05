package facade;

import logic.controller.partner.PartnerController;
import org.bson.types.ObjectId;
import persistence.entity.partner.Partner;

import java.util.List;

/**
 * Specific facade for Partners.
 */
class PartnerFacade {

    /**
     * Singleton instance.
     */
    private static PartnerFacade instance;

    private PartnerFacade() { }

    /**
     * Get the singleton instance of the partner facade.
     *
     * @return The singleton instance.
     */
    public static PartnerFacade getInstance() {
        if (instance == null) {
            instance = new PartnerFacade();
        }
        return instance;
    }

    /**
     * Insert a partner.
     *
     * @param partner The partner to insert.
     * @return The id of the inserted partner.
     */
    protected ObjectId insertOnePartner(Partner partner) {
        return PartnerController.getInstance().insertOne(partner);
    }

    /**
     * Get all partners.
     *
     * @return A list of partners.
     */
    protected List<Partner> getPartnerList() {
        return PartnerController.getInstance().findAll();
    }

    /**
     * Get a partner by its id.
     *
     * @param id The id of the partner.
     * @return The partner or null if not found.
     */
    protected Partner getOnePartner(ObjectId id) {
        return PartnerController.getInstance().findOne(id);
    }

    /**
     * Update a partner.
     *
     * @param id The id of the partner to update.
     * @param partner The new partner.
     * @return true if the partner has been updated, false otherwise.
     */
    protected boolean updateOnePartner(ObjectId id, Partner partner) {
        return PartnerController.getInstance().updateOne(id, partner);
    }

    /**
     * Delete a partner.
     *
     * @param id The id of the partner to delete.
     * @return true if the partner has been deleted, false otherwise.
     */
    protected boolean deleteOnePartner(ObjectId id) {
        return PartnerController.getInstance().deleteOne(id);
    }
}
