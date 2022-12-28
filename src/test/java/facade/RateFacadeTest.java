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

    private final FacadeInterface facade = Facade.getInstance();
    private Rate rate;
    private Rate rateSubject;
    private Rate rateSubject2;

    @BeforeEach
    void init() {
        ObjectId ownerRef = new ObjectId("6398c428ba6578737cbd9184");
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,13);
        Date date = cal.getTime();
        rate = new Rate(5, "il est très bon", false, date);
        rateSubject = new Rate(ownerRef,3, "bof", false, date );
        rateSubject2 = new Rate(ownerRef,4, "pas mal du tout", false, date );
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

    /**
     * Test the getRateListFromUser method.
     */
    @Test
    void getRateListFromUser(){
        try {
            ObjectId userId = rateSubject.getOwnerRef();

            ObjectId rateId = facade.insertOneRate(rateSubject);

            List<Rate> ratesFromUserBefore = facade.getRateListFromUser(userId);

            int sizeBefore = ratesFromUserBefore.size();

            ObjectId rateId1 = facade.insertOneRate(rateSubject);

            List<Rate> ratesFromUserAfter = facade.getRateListFromUser(userId);

            int sizeAfter = ratesFromUserAfter.size();

            assertEquals(sizeBefore + 1, sizeAfter);

            // CLEAN UP

            facade.deleteOneRate(rateId);
            facade.deleteOneRate(rateId1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
