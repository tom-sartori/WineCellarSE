package logic.controller.partner;

import logic.controller.AbstractController;
import persistence.entity.partner.Partner;
import persistence.dao.AbstractDao;
import persistence.dao.partner.PartnerDao;

/**
 * PartnerController class extending Controller class parametrized with Partner class.
 */
public class PartnerController extends AbstractController<Partner> {

    /**
     * Instance of PartnerController to ensure Singleton design pattern.
     */
    private static PartnerController instance;

    /**
     * Private constructor for PartnerController to ensure Singleton design pattern.
     */
    private PartnerController() { }

    /**
     * @return the instance of PartnerController to ensure Singleton design pattern.
     */
    public static PartnerController getInstance() {
        if(instance == null){
            instance = new PartnerController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (PartnerDao).
     */
    @Override
    protected AbstractDao<Partner> getDao() {
        return PartnerDao.getInstance();
    }
}
