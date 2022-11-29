package facade;

import logic.controller.partner.PartnerController;
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
     * Get all partners.
     *
     * @return A list of partners.
     */
    protected List<Partner> getPartnerList() {
        return PartnerController.getInstance().findAll();
    }
}
