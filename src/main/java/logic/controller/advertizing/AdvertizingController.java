package logic.controller.advertizing;

import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import persistence.entity.advertizing.Advertizing;
import persistence.dao.AbstractDao;
import persistence.dao.advertizing.AdvertizingDao;

import java.util.Date;

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
     * Renew an advertizing.
     *
     * @param id The id of the advertizing to renew.
     * @param endDate The new end date of the advertizing.
     * @return true if the advertizing has been renewed, false otherwise.
     */
    public boolean renewOneAdvertizing(ObjectId id, Date endDate) {
        Advertizing updated = getDao().findOne(id);
        updated.setEndDate(endDate);
        updated.setActive(true);
        return this.updateOne(id, updated);
    }

    /**
     * Pay for an advertizing.
     *
     * @param id The id of the advertizing to pay for.
     * @return true if the advertizing has been paid, false otherwise.
     */
    public boolean payOneAdvertizing(ObjectId id) {
        Advertizing payed = getDao().findOne(id);
        payed.setPayed(true);
        return this.updateOne(id, payed);
    }

    /**
     * add view to an advertizing.
     *
     * @param id The id of the advertizing.
     * @return true if the view was added to advertizing, false otherwise.
     */
    public boolean addView(ObjectId id) {
        Advertizing updated = getDao().findOne(id);
        int newNb = updated.getNbViews() + 1;
        updated.setNbViews(newNb);
        return this.updateOne(id, updated);
    }

    /**
     * validate an advertizing.
     *
     * @param id The id of the advertizing to validate.
     * @return true if the advertizing has been validated, false otherwise.
     */
    public boolean validateAdvertizing(ObjectId id) {
        Advertizing validated = getDao().findOne(id);
        if (validated.isPayed() && validated.getStartDate().before(validated.getEndDate())){
            validated.setActive(true);
            return this.updateOne(id, validated);
        }
        return false;
    }

    /**
     * @return the DAO of the specific Controller (AdvertizingDao).
     */
    @Override
    protected AbstractDao<Advertizing> getDao() {
        return AdvertizingDao.getInstance();
    }
}
