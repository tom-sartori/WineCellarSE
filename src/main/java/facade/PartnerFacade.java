package facade;

import controller.partner.PartnerController;
import entity.partner.Partner;

import java.util.List;

class PartnerFacade {

    private static PartnerFacade instance;
    // TODO Comments partoux
    private PartnerFacade() { }

    public static PartnerFacade getInstance() {
        if (instance == null) {
            instance = new PartnerFacade();
        }
        return instance;
    }

    protected List<Partner> getPartnerList() {
        return PartnerController.getInstance().findAll();
    }
}
