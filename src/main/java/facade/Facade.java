package facade;

import entity.partner.Partner;
import facade.partner.PartnerFacade;

public class Facade implements FacadeInterface {

    private static Facade instance = null;

    private final PartnerFacade partnerFacade;

    private Facade() {
        this.partnerFacade = PartnerFacade.getInstance();
    }

    public static Facade getInstance(){
        if (instance == null){
            instance = new Facade();
        }
        return instance;
    }

    public Partner getPartners() {
        return partnerFacade.getPartners();
    }
}
