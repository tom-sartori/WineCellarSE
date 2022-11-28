package facade;

import entity.partner.Partner;

public class Facade implements FacadeInterface {

    private static Facade instance;

    private Facade() { }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public Partner getPartners() {
        return PartnerFacade.getInstance().getPartners();
    }
}
