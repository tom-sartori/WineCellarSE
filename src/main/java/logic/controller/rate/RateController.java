package logic.controller.rate;

import logic.controller.AbstractController;
import org.bson.BsonDocument;
import persistence.dao.rate.RateDao;
import persistence.entity.Entity;
import persistence.entity.rate.Rate;

import java.util.Collections;
import java.util.List;

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


    /**
     * implementation to find all rates with filter of the parametrized type.
     *
     * @param filter The filter to apply
     * @return The list of all rates with filter.
     */

    public List<Rate> findAllWithFilter(BsonDocument filter) throws Exception {
        List<Rate> all = getDao().findAllWithFilter(filter);
        all.forEach((Entity::handleOnFind));
        Collections.sort(all);
        return all;
    }
}
