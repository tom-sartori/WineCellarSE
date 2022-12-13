package logic.controller.referencing;

import logic.controller.AbstractController;
import persistence.dao.referencing.ReferencingDao;
import persistence.entity.referencing.Referencing;

/**
 * ReferencingController class extending Controller class parametrized with Referencing class.
 */
public class ReferencingController extends AbstractController<Referencing> {

    /**
     * Instance of ReferencingController to ensure Singleton design pattern.
     */
    private static ReferencingController instance;

    /**
     * Private constructor for ReferencingController to ensure Singleton design pattern.
     */
    private ReferencingController() { }

    /**
     * @return the instance of ReferencingController to ensure Singleton design pattern.
     */
    public static ReferencingController getInstance() {
        if(instance == null){
            instance = new ReferencingController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (ReferencingDao).
     */
    @Override
    protected ReferencingDao getDao() {
        return ReferencingDao.getInstance();
    }
}
