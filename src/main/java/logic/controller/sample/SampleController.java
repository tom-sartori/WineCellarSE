package logic.controller.sample;

import logic.controller.AbstractController;
import persistence.dao.mongodb.sample.SampleMongoDao;
import persistence.entity.sample.Sample;

/**
 * SampleController class extending Controller class parametrized with Sample class.
 */
public class SampleController extends AbstractController<Sample> {

    /**
     * Instance of SampleController to ensure Singleton design pattern.
     */
    private static SampleController instance;

    /**
     * Private constructor for SampleController to ensure Singleton design pattern.
     */
    private SampleController() { }

    /**
     * @return the instance of SampleController to ensure Singleton design pattern.
     */
    public static SampleController getInstance() {
        if(instance == null){
            instance = new SampleController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (SampleDao).
     */
    @Override
    protected SampleMongoDao getDao() {
        return SampleMongoDao.getInstance();
    }
}
