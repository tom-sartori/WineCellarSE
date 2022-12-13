package facade;

import org.bson.types.ObjectId;
import persistence.entity.guide.Guide;
import persistence.entity.partner.Partner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Global facade partner interface.
 * This interface is used to define the methods that will be used by the UI.
 */
public class Facade implements FacadeInterface {

    /**
     * Singleton instance.
     */
    private static Facade instance;

    private Facade() { }

    /**
     * Get the singleton instance of the facade.
     *
     * @return The singleton instance.
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * Insert a partner.
     *
     * @param partner The partner to insert.
     * @return The id of the inserted partner.
     */
    @Override
    public ObjectId insertOnePartner(Partner partner) {
        return PartnerFacade.getInstance().insertOnePartner(partner);
    }

    /**
     * Get all partners.
     *
     * @return A list of partners.
     */
    @Override
    public List<Partner> getPartnerList() {
        return PartnerFacade.getInstance().getPartnerList();
    }

    /**
     * Get a partner by its id.
     *
     * @param id The id of the partner.
     * @return The partner.
     */
    @Override
    public Partner getOnePartner(ObjectId id) {
        return PartnerFacade.getInstance().getOnePartner(id);
    }

    /**
     * Update a partner.
     *
     * @param id The id of the partner to update.
     * @param partner The new partner.
     * @return true if the partner has been updated, false otherwise.
     */
    @Override
    public boolean updateOnePartner(ObjectId id, Partner partner) {
        return PartnerFacade.getInstance().updateOnePartner(id, partner);
    }

    /**
     * Delete a partner.
     *
     * @param id The id of the partner to delete.
     * @return true if the partner has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOnePartner(ObjectId id) {
        return PartnerFacade.getInstance().deleteOnePartner(id);
    }

    /**
     * Insert a guide.
     *
     * @param guide The partner to insert.
     * @return The id of the inserted guide.
     */
    @Override
    public ObjectId insertOneGuide(Guide guide) {
        return GuideFacade.getInstance().insertOneGuide(guide);
    }

    @Override
    public List<Guide> getGuideList() {
        return GuideFacade.getInstance().getGuideList();
    }

    @Override
    public Guide getOneGuide(ObjectId id) {
        return GuideFacade.getInstance().getOneGuide(id);
    }

    @Override
    public boolean updateOneGuide(ObjectId id, Guide guide) {
        return GuideFacade.getInstance().updateOneGuide(id, guide);
    }

    @Override
    public boolean deleteOneGuide(ObjectId id) {
        return GuideFacade.getInstance().deleteOneGuide(id);
    }










}
