package facade;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.rate.Rate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RateFacadeTest {

    private final RateFacade facade = RateFacade.getInstance();
    private Rate rate;
    private Rate rateTest;
    private Rate rateSubject;
    private Rate rateSubject2;
    private ObjectId idsubjet;

    @BeforeEach
    void init() {
        idsubjet = new ObjectId("6398c428ba6578737cbd9184");
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,13);
        Date date = cal.getTime();
        rate = new Rate(5, "il est très bon", false, date);
        rateTest = new Rate(6, "il est incroyable", false, date);
        rateSubject = new Rate(idsubjet,3, "bof", false, date );
        rateSubject2 = new Rate(idsubjet,4, "pas mal du tout", false, date );
    }

    @Test
    void test_create_OK() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        rate.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneRate(rate);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Rate receivedRate = facade.getOneRate(idReceivedAfterCreation);

        assertEquals(rate.getRate(), receivedRate.getRate());
        assertEquals(rate.getLastModified(), receivedRate.getLastModified());


    }
    @Test
    void test_create_OK2() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        rateSubject.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneRate(rateSubject);
        facade.insertOneRate(rateSubject2);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Rate receivedRate = facade.getOneRate(idReceivedAfterCreation);

        assertEquals(rateSubject.getRate(), receivedRate.getRate());
        assertEquals(rateSubject.getLastModified(), receivedRate.getLastModified());
    }

    @Test
    void test_findAll_OK() {
        int initialNumberOfEvents = facade.getRateList().size();

        ObjectId idOfInsertedRate = facade.insertOneRate(rate);
        facade.insertOneRate(rate);
        facade.insertOneRate(rate);

        List<Rate> receivedRateList = facade.getRateList();

        assertEquals(3 + initialNumberOfEvents, receivedRateList.size());
        assertTrue(receivedRateList.contains(facade.getOneRate(idOfInsertedRate)));
    }

    @Test
    void test_find_with_filter() throws Exception {
        BsonDocument filter = new BsonDocument();
        filter.append("subject", new org.bson.BsonObjectId(idsubjet));
        int initialNumberOfEvents = facade.getRateListWithFilter(filter).size();

        ObjectId idOfInsertedRate = facade.insertOneRate(rateSubject);
        facade.insertOneRate(rateSubject);
        facade.insertOneRate(rateSubject);

        List<Rate> receivedRateList = facade.getRateListWithFilter(filter);

        assertEquals(3 + initialNumberOfEvents, receivedRateList.size());
        assertTrue(receivedRateList.contains(facade.getOneRate(idOfInsertedRate)));
    }


    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedRate = facade.insertOneRate(rate);

        Rate receivedRate = facade.getOneRate(idOfInsertedRate);

        assertEquals(rate.getRate(), receivedRate.getRate());
        assertEquals(rate.getLastModified(), receivedRate.getLastModified());
    }

    @Test
    void test_update_OK() {
        ObjectId idOfInsertedRate = facade.insertOneRate(rate);

        Rate receivedRate = facade.getOneRate(idOfInsertedRate);
        receivedRate.setRate(4);
        receivedRate.setComment("Après reflexion il a perdu du gout");
        receivedRate.setModified(true);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,14);
        Date date = cal.getTime();
        receivedRate.setLastModified(date);

        System.out.println(receivedRate.getId());
        facade.updateOneRate(receivedRate.getId(), receivedRate);

        Rate updatedRate = facade.getOneRate(idOfInsertedRate);

        assertEquals(receivedRate.getRate(), updatedRate.getRate());
        assertEquals(receivedRate.getLastModified(), updatedRate.getLastModified());
        assertEquals(true, updatedRate.isModified());

    }

    @Test
    void test_delete_OK() {
        ObjectId rateIdInserted = facade.insertOneRate(rate);
        rate.setId(rateIdInserted);
        List<Rate> rateList = facade.getRateList();
        int initialNumberOfRates = rateList.size();
        assertTrue(rateList.contains(facade.getOneRate(rateIdInserted)));

        assertTrue(facade.deleteOneRate(rateIdInserted));
        assertEquals(initialNumberOfRates - 1, facade.getRateList().size());
    }

}
