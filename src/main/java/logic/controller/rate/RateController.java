package logic.controller.rate;

import exception.NotFoundException;
import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.rate.RateMongoDao;
import persistence.entity.rate.Rate;

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
    protected RateMongoDao getDao() {
        return RateMongoDao.getInstance();
    }


    /**
     * Get all the rates of a user.
     *
     * @param userId The id of the user.
     *
     * @return A list of all the rates of the user if there are any, otherwise throws a NotFoundException.
     */
    public List<Rate> getRateListFromUser(ObjectId userId) throws NotFoundException {
        return RateMongoDao.getInstance().getRateListFromUser(userId);
    }

    /**
     * Get all the rates of a subject.
     *
     * @param subjectId The id of the subject.
     *
     * @return A list of all the rates of the subject if there are any, otherwise throws a NotFoundException.
     */
    public List<Rate> getRateListFromSubject(ObjectId subjectId) throws NotFoundException {
        return RateMongoDao.getInstance().getRateListFromSubject(subjectId);
    }


}
