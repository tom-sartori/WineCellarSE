package logic.controller.rate;

import logic.controller.AbstractController;
import persistence.dao.rate.RateDao;
import persistence.entity.rate.Rate;

/**
 * RateController class extending Controller class parametrized with Rate class.
 */
public class RateController extends AbstractController<Rate> {

    /**
     * Instance of RateController to ensure Singleton design pattern.
     */
    private static RateController instance;

    /**
     * Private constructor for RateController to ensure Singleton design pattern.
     */
    private RateController() { }

    /**
     * @return the instance of RateController to ensure Singleton design pattern.
     */
    public static RateController getInstance() {
        if(instance == null){
            instance = new RateController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (RateDao).
     */
    @Override
    protected RateDao getDao() {
        return RateDao.getInstance();
    }
}
