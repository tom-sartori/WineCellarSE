package facade;

import entity.partner.Partner;

import java.util.List;

/**
 * Global facade partner interface.
 * This interface is used to define the methods that will be used by the UI.
 */
public class Facade implements FacadeInterface {

    /**
     * Singleton instance.
     */
    private static Facade instance;

    private Facade() { }

    /**
     * Get the singleton instance of the facade.
     *
     * @return The singleton instance.
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * Get all partners.
     *
     * @return A list of partners.
     */
    public List<Partner> getPartnerList() {
        return PartnerFacade.getInstance().getPartnerList();
    }
}
