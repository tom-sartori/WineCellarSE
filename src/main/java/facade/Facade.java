package facade;

import entity.partner.Partner;

import java.util.List;

public class Facade implements FacadeInterface {

    private static Facade instance;

    private Facade() { }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public List<Partner> getPartnerList() {
        return PartnerFacade.getInstance().getPartnerList();
    }
}
