package facade;

import controller.partner.PartnerController;
import entity.partner.Partner;

class PartnerFacade {

    private static PartnerFacade instance;

    private final PartnerController partnerController;

    private PartnerFacade() {
        partnerController = new PartnerController();
    }

    public static PartnerFacade getInstance() {
        if (instance == null) {
            instance = new PartnerFacade();
        }
        return instance;
    }

    protected Partner getPartners() {
        return partnerController.getPartners();
    }
}
