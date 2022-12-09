package logic.controller.advertizing;

import logic.controller.AbstractController;
import persistence.entity.advertizing.Advertizing;
import persistence.dao.AbstractDao;
import persistence.dao.advertizing.AdvertizingDao;

/**
 * AdvertizingController class extending Controller class parametrized with Advertizing class.
 */
public class AdvertizingController extends AbstractController<Advertizing> {

    /**
     * Instance of AdvertizingController to ensure Singleton design pattern.
     */
    private static AdvertizingController instance;

    /**
     * Private constructor for AdvertizingController to ensure Singleton design pattern.
     */
    private AdvertizingController() { }

    /**
     * @return the instance of AdvertizingController to ensure Singleton design pattern.
     */
    public static AdvertizingController getInstance() {
        if(instance == null){
            instance = new AdvertizingController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (AdvertizingDao).
     */
    @Override
    protected AbstractDao<Advertizing> getDao() {
        return AdvertizingDao.getInstance();
    }
}
