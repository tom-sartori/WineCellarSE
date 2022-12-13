package logic.controller.referencing;

import logic.controller.AbstractController;
import org.bson.BsonDocument;
import persistence.dao.referencing.ReferencingDao;
import persistence.entity.referencing.Referencing;

import java.util.List;

/**
 * ReferencingController class extending Controller class parametrized with Referencing class.
 */

///TODO: Autres fonctions nécessaires?
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
     * Get referencings by their importanceLevel.
     *
     * @param importanceLevel The level of importance of the searched referencings.
     * @return A list of referencings.
     */
    public List<Referencing> findAllByLevel(int importanceLevel) {
        BsonDocument filter = new BsonDocument();
        filter.append("importanceLevel", new org.bson.BsonInt64(importanceLevel));
        return getDao().findWithFilter(filter);
    }

    /**
     * @return the DAO of the specific Controller (ReferencingDao).
     */
    @Override
    protected ReferencingDao getDao() {
        return ReferencingDao.getInstance();
    }
}
