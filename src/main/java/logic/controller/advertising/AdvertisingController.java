package logic.controller.advertising;

import logic.controller.AbstractController;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import persistence.entity.advertising.Advertising;
import persistence.dao.AbstractDao;
import persistence.dao.advertising.AdvertisingDao;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * AdvertisingController class extending Controller class parametrized with Advertising class.
 */
public class AdvertisingController extends AbstractController<Advertising> {

    /**
     * Instance of AdvertisingController to ensure Singleton design pattern.
     */
    private static AdvertisingController instance;

    /**
     * Private constructor for AdvertisingController to ensure Singleton design pattern.
     */
    private AdvertisingController() { }

    /**
     * @return the instance of AdvertisingController to ensure Singleton design pattern.
     */
    public static AdvertisingController getInstance() {
        if(instance == null){
            instance = new AdvertisingController();
        }
        return instance;
    }

    //TODO : erreur si nouvelle endDate avant now

    /**
     * Renew an advertising.
     *
     * @param id The id of the advertising to renew.
     * @param endDate The new end date of the advertising.
     * @return true if the advertising has been renewed, false otherwise.
     */
    public boolean renewOneAdvertising(ObjectId id, Date endDate) {
        Advertising updated = getDao().findOne(id);
        Date now = new Date();
        if(now.before(endDate)){ //The new endDate is valid
            if(updated.getEndDate().before(now)){ //The advertising was expired
                updated.setActive(true);
            }
            updated.setPrice((updated.getEndDate().getTime() - endDate.getTime())/(8640000));
            updated.setEndDate(endDate);
            updated.setPayed(false);
        }
        return this.updateOne(id, updated);
    }

    /**
     * Pay for an advertising.
     *
     * @param id The id of the advertising to pay for.
     * @return true if the advertising has been paid, false otherwise.
     */
    public boolean payOneAdvertising(ObjectId id) {
        Advertising payed = getDao().findOne(id);
        payed.setPayed(true);
        return this.updateOne(id, payed);
    }

    /**
     * add view to an advertising.
     *
     * @param id The id of the advertising.
     * @return true if the view was added to advertising, false otherwise.
     */
    public boolean addView(ObjectId id) {
        Advertising updated = getDao().findOne(id);
        int newNb = updated.getNbViews() + 1;
        updated.setNbViews(newNb);
        return this.updateOne(id, updated);
    }

    /**
     * validate an advertising.
     *
     * @param id The id of the advertising to validate.
     * @return true if the advertising has been validated, false otherwise.
     */
    public boolean validateAdvertising(ObjectId id) {
        Advertising validated = getDao().findOne(id);
        if (validated.isPayed() && validated.getStartDate().before(validated.getEndDate())){
            validated.setActive(true);
            return this.updateOne(id, validated);
        }
        return false;
    }

    /**
     * @return the DAO of the specific Controller (AdvertisingDao).
     */
    @Override
    protected AbstractDao<Advertising> getDao() {
        return AdvertisingDao.getInstance();
    }

    /**
     * Get advertising by their company id.
     *
     * @param company The id of the advertised company.
     * @return A list of advertisings.
     */
    public List<Advertising> findAllByCompany(ObjectId company) {
        BsonDocument filter = new BsonDocument();
        filter.append("company", new org.bson.BsonObjectId(company));
        return getDao().findAllWithFilter(filter);
    }

    /**
     * Get advertisings not validated.
     *
     * @return A list of advertisings.
     */
    public List<Advertising> findAllNotValidated() {
        BsonDocument filter = new BsonDocument();
        filter.append("active", new org.bson.BsonBoolean(false));
        return getDao().findAllWithFilter(filter);
    }

    /**
     * Get a random validated advertising.
     *
     * @return An advertising.
     */
    public Advertising findRandom() {
        BsonDocument filter = new BsonDocument();
        filter.append("active", new org.bson.BsonBoolean(true));
        List<Advertising> validated = getDao().findAllWithFilter(filter);
        Random random = new Random();
        Advertising randomAdvertising = validated.get(random.nextInt(validated.size()));
        return randomAdvertising;
    }

    /**
     * Calculate the price of an advertising.
     *
     * @param startDate The start date of the advertising.
     * @param endDate The end date of the advertising.
     * @return The price.
     */
    public double calculatePrice(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime())/(8640000);
    }
}
