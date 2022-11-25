package facade;

import controller.PartnerController;
import model.Partner;

public class PartnerFacade {

    private PartnerController partnerController;

    public PartnerFacade() {
        partnerController = new PartnerController();
    }

    public Partner getPartners(){
        return partnerController.getPartners();
    }
}
