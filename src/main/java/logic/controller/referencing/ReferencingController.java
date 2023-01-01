package logic.controller.referencing;

import facade.Facade;
import logic.controller.AbstractController;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import persistence.dao.referencing.ReferencingDao;
import persistence.entity.referencing.Referencing;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
    private ReferencingController() {
    }

    /**
     * @return the instance of ReferencingController to ensure Singleton design pattern.
     */
    public static ReferencingController getInstance() {
        if (instance == null) {
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

    /**
     * Get referencings by their company id.
     *
     * @param company The id of the referenced company.
     * @return A list of referencings.
     */
    public List<Referencing> findAllByCompany(ObjectId company) {
        BsonDocument filter = new BsonDocument();
        filter.append("company", new org.bson.BsonObjectId(company));
        return getDao().findAllWithFilter(filter);
    }

    /**
     * Update the status of a referencing thanks to its startDate and endDate, with the values "Passé", "En cours" and "A venir"
     *
     * @param id          The id of the referencing.
     * @param referencing The referencing.
     * @return A list of referencings.
     */
    public boolean updateStatus(ObjectId id, Referencing referencing) {
        Date now = new Date();
        if (referencing.getStartDate().before(now)) {
            if (now.before(referencing.getExpirationDate())) {
                if (referencing.getStatus() != "En cours") {
                    referencing.setStatus("En cours");
                    return getDao().updateOne(id, referencing);
                }
            }
            if (referencing.getStatus() != "Passé") {
                referencing.setStatus("Passé");
                return getDao().updateOne(id, referencing);
            }
        }
        if (referencing.getStatus() != "A venir") {
            referencing.setStatus("A venir");
            return getDao().updateOne(id, referencing);
        }
        return true;
    }

    /**
     * Get a random validated referencing.
     *
     * @return A Referencing.
     */
    public Referencing findRandom() {
        List<Referencing> referencingList = Facade.getInstance().getReferencingList();
        Random rand = new Random();
        Referencing randomElement = referencingList.get(rand.nextInt(referencingList.size()));
        return randomElement;
    }

    /**
     * Calculate the price of a referencing.
     *
     * @param startDate The start date of the referencing.
     * @param endDate   The end date of the referencing.
     * @return The price.
     */
    public double calculatePrice(Date startDate, Date endDate, int importanceLevel) {
        return ((endDate.getTime() - startDate.getTime()) * importanceLevel) / (8640000);
    }

    /**
     * @return the DAO of the specific Controller (ReferencingDao).
     */
    @Override
    protected ReferencingDao getDao() {
        return ReferencingDao.getInstance();
    }
}
