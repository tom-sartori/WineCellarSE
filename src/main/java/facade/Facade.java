package facade;

import entity.partner.Partner;
import facade.partner.PartnerFacade;

public class Facade implements FacadeInterface {
    /// TODO : Make this a singleton.

    private final PartnerFacade partnerFacade;

    public Facade() {
        this.partnerFacade = new PartnerFacade();
    }

    public Partner getPartners() {
        return partnerFacade.getPartners();
    }
}
