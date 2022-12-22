package logic.controller.referencing;

import logic.controller.AbstractController;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import persistence.dao.referencing.ReferencingDao;
import persistence.entity.referencing.Referencing;

import java.sql.Ref;
import java.util.Date;
import java.util.List;

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
     * Get referencings by their importanceLevel.
     *
     * @param importanceLevel The level of importance of the searched referencings.
     * @return A list of referencings.
     */
    public List<Referencing> findAllByLevel(int importanceLevel) {
        BsonDocument filter = new BsonDocument();
        filter.append("importanceLevel", new org.bson.BsonInt64(importanceLevel));
        return getDao().findAllWithFilter(filter);
    }

    public boolean updateStatus(ObjectId id, Referencing referencing){
        Date now = new Date();
        if(referencing.getStartDate().before(now)){
            if(now.before(referencing.getExpirationDate())){
                if(referencing.getStatus() != "En cours"){
                    referencing.setStatus("En cours");
                    return getDao().updateOne(id,referencing);
                }
            }
            if(referencing.getStatus() != "Passe"){
                referencing.setStatus("Passe");
                return getDao().updateOne(id, referencing);
            }
        }
        if(referencing.getStatus() != "A venir"){
            referencing.setStatus("A venir");
            return getDao().updateOne(id, referencing);
        }
        return true;
    }

    /**
     * @return the DAO of the specific Controller (ReferencingDao).
     */
    @Override
    protected ReferencingDao getDao() {
        return ReferencingDao.getInstance();
    }
}
