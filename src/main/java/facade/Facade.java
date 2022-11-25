package facade;

import model.Partner;

public class Facade {

    private PartnerFacade partnerFacade;

    public Facade() {
        this.partnerFacade = new PartnerFacade();
    }

    public Partner getPartners(){
        return partnerFacade.getPartners();
    }

}
