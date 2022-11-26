package facade.partner;

import controller.partner.PartnerController;
import entity.partner.Partner;

public class PartnerFacade {

    private static PartnerFacade instance = null;

    private final PartnerController partnerController;

    private PartnerFacade() {
        partnerController = new PartnerController();
    }

    public static PartnerFacade getInstance(){
        if(instance == null){
            instance = new PartnerFacade();
        }
        return instance;
    }

    public Partner getPartners(){
        return partnerController.getPartners();
    }
}
