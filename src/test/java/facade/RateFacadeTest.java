package facade;

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

    @BeforeEach
    void init() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,13);
        Date date = cal.getTime();
        rate = new Rate("5 etoiles", "il est très bon", false, date);
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
        receivedRate.setRate("4 étoiles");
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
