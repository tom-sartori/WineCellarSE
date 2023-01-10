package logic.controller.guide;

import logic.controller.AbstractController;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.dao.mongodb.guide.GuideMongoDao;
import persistence.entity.guide.Guide;

/**
 * GuideController class extending Controller class parametized with Guide class.
 */
public class GuideController extends AbstractController<Guide> {


    /**
     * Instance of GuideController to ensure Singleton design pattern.
     */
    private static GuideController instance;

    /**
     * Private constructor for GuideController to ensure Singleton design pattern.
     */
    private GuideController() { }

    /**
     * @return the instance of GuideController to ensure Singleton design pattern.
     */
    public static GuideController getInstance() {
        if(instance == null){
            instance = new GuideController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (GuideDao).
     */
    @Override
    protected AbstractMongoDao<Guide> getDao() {
        return GuideMongoDao.getInstance();
    }

}
