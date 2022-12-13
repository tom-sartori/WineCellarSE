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

    /// TODO : Validate teste isPayed et d'autres trucs ?
    /**
     * Pay for an advertizing.
     *
     * @param id The id of the advertizing to pay for.
     * @return true if the advertizing has been payed, false otherwise.
     */
    public boolean payOneAdvertizing(ObjectId id) {
        Advertizing payed = getDao().findOne(id);
        payed.setPayed(true);
        return this.updateOne(id, payed);
    }

    /**
     * add views to an advertizing.
     *
     * @param id The id of the advertizing to pay for.
     * @param number The number of views to add.
     * @return true if the advertizing has been paid, false otherwise.
     */
    public boolean addViews(ObjectId id, int number) {
        Advertizing updated = getDao().findOne(id);
        int newNb = updated.getNbViews() + number;
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
        validated.setActive(true);
        return this.updateOne(id, validated);
    }

    /**
     * @return the DAO of the specific Controller (AdvertizingDao).
     */
    @Override
    protected AbstractDao<Advertizing> getDao() {
        return AdvertizingDao.getInstance();
    }
}
