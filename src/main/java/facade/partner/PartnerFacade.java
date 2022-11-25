package facade.partner;

import controller.partner.PartnerController;
import entity.partner.Partner;

public class PartnerFacade {
    /// TODO : Make this a singleton.

    private final PartnerController partnerController;

    public PartnerFacade() {
        partnerController = new PartnerController();
    }

    public Partner getPartners(){
        return partnerController.getPartners();
    }
}
