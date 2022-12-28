package facade;

import javafx.scene.control.TableView;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.referencing.Referencing;

import java.sql.Ref;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReferencingFacadeTest {

    private final Facade facade = Facade.getInstance();
    private Referencing referencing = new Referencing();

    @BeforeEach
    void init() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.DECEMBER,13);
        Date payment = cal.getTime();

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2022, Calendar.DECEMBER,15);
        Date debut = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2023, Calendar.JANUARY,17);
        Date fin = cal2.getTime();
        ObjectId company = new ObjectId("63a5c45048f0c9194a9295ef");
        referencing.setStartDate(debut);
        referencing.setPaymentDate(payment);
        referencing.setImportanceLevel(1);
        referencing.setExpirationDate(fin);
        referencing.setCompany(company);
    }


    /**
     * Test if a referencing can be created, with an id automatically generated by the database and not the user.
     */
    @Test
    void test_create_OK() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        referencing.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneReferencing(referencing);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Referencing receivedEvent = facade.getOneReferencing(idReceivedAfterCreation);
        assertEquals(referencing.getStartDate(), receivedEvent.getStartDate());
        assertEquals(referencing.getPaymentDate(), receivedEvent.getPaymentDate());
        facade.deleteOneReferencing(idReceivedAfterCreation);
    }

    /**
     * Test if the referencing can be all found.
     */
    @Test
    void test_findAll_OK() {
        int initialNumberOfReferencing = facade.getReferencingList().size();

        ObjectId idOfInsertedReferencing = facade.insertOneReferencing(referencing);
        ObjectId id2 = facade.insertOneReferencing(referencing);
        ObjectId id3 = facade.insertOneReferencing(referencing);

        List<Referencing> receivedReferencingList = facade.getReferencingList();

        assertEquals(3 + initialNumberOfReferencing, receivedReferencingList.size());
        assertTrue(receivedReferencingList.contains(facade.getOneReferencing(idOfInsertedReferencing)));
        facade.deleteOneReferencing(idOfInsertedReferencing);
        facade.deleteOneReferencing(id2);
        facade.deleteOneReferencing(id3);
    }


    /**
     * Test if a referencing can be found thanks to its id.
     */
    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedReferencing = facade.insertOneReferencing(referencing);

        Referencing receivedReferencing = facade.getOneReferencing(idOfInsertedReferencing);

        assertEquals(referencing.getPrice(), receivedReferencing.getPrice());
        assertEquals(referencing.getImportanceLevel(), receivedReferencing.getImportanceLevel());
        assertEquals(referencing.getStartDate(), receivedReferencing.getStartDate());
        assertEquals(referencing.getStatus(), receivedReferencing.getStatus());
        assertEquals(referencing.getExpirationDate(), receivedReferencing.getExpirationDate());
        assertEquals(referencing.getPaymentDate(), receivedReferencing.getPaymentDate());
        facade.deleteOneReferencing(idOfInsertedReferencing);
    }


    /**
     * Test if the attributes of a referencing can be updated thanks to its id.
     */
    @Test
    void test_update_OK() {
        ObjectId idOfInsertedReferencing = facade.insertOneReferencing(referencing);

        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.JANUARY,16);
        Date payment = cal.getTime();

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2022, Calendar.JANUARY,18);
        Date debut = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2022, Calendar.JANUARY,30);
        Date fin = cal2.getTime();

        Referencing receivedEvent = facade.getOneReferencing(idOfInsertedReferencing);
        receivedEvent.setImportanceLevel(3);
        receivedEvent.setPaymentDate(payment);
        receivedEvent.setStartDate(debut);
        receivedEvent.setExpirationDate(fin);

        facade.updateOneReferencing(receivedEvent.getId(), receivedEvent);

        Referencing updatedReferencing = facade.getOneReferencing(idOfInsertedReferencing);

        assertEquals(receivedEvent.getPaymentDate(), updatedReferencing.getPaymentDate());
        assertEquals(receivedEvent.getExpirationDate(), updatedReferencing.getExpirationDate());
        assertEquals(receivedEvent.getStartDate(), updatedReferencing.getStartDate());
        assertEquals(receivedEvent.getImportanceLevel(), updatedReferencing.getImportanceLevel());
        facade.deleteOneReferencing(idOfInsertedReferencing);
    }

    /**
     * Test if the status of a referencing can be updated thanks to its id, endDate and startDate.
     */
    @Test
    void test_updateStatus_OK() {
        ObjectId idOfInsertedReferencing = facade.insertOneReferencing(referencing);

        Date tomorrow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(tomorrow);
        c.add(Calendar.DATE, 1);
        tomorrow = c.getTime();

        referencing.setStartDate(tomorrow);

        facade.updateStatus(idOfInsertedReferencing, referencing);
        Referencing updatedAVenir = facade.getOneReferencing(idOfInsertedReferencing);
        assertEquals(updatedAVenir.getStatus(), "A venir");

        Date now = new Date();
        referencing.setStartDate(now);
        facade.updateOneReferencing(idOfInsertedReferencing, referencing);

        facade.updateStatus(idOfInsertedReferencing, referencing);
        Referencing updatedEnCours = facade.getOneReferencing(idOfInsertedReferencing);
        assertEquals(updatedEnCours.getStatus(), "En cours");

        Date yesterday = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(yesterday);
        c2.add(Calendar.DATE, -1);
        yesterday = c2.getTime();
        referencing.setExpirationDate(yesterday);

        facade.updateStatus(idOfInsertedReferencing, referencing);
        Referencing updatedPasse = facade.getOneReferencing(idOfInsertedReferencing);
        assertEquals(updatedPasse.getStatus(), "Passe");

        facade.deleteOneReferencing(idOfInsertedReferencing);
    }

    /**
     * Test if a referencing can be deleted thanks to its id.
     */
    @Test
    void test_delete_OK() {
        ObjectId eventIdInserted = facade.insertOneReferencing(referencing);
        referencing.setId(eventIdInserted);
        List<Referencing> referencingList = facade.getReferencingList();
        int initialNumberOfReferencing = referencingList.size();
        assertTrue(referencingList.contains(facade.getOneReferencing(eventIdInserted)));

        assertTrue(facade.deleteOneReferencing(eventIdInserted));
        assertEquals(initialNumberOfReferencing - 1, facade.getReferencingList().size());
    }

    /**
     * Test if a referencing can be found thanks to its level of importance.
     */
    @Test
    void test_findByLevel_OK() {

        referencing.setImportanceLevel(1);
        ObjectId idOfInsertedReferencing = facade.insertOneReferencing(referencing);
        referencing.setImportanceLevel(12);
        ObjectId idOfInsertedReferencing1 = facade.insertOneReferencing(referencing);
        ObjectId idOfInsertedReferencing2 = facade.insertOneReferencing(referencing);
        ObjectId idOfInsertedReferencing3 = facade.insertOneReferencing(referencing);

        List<Referencing> referencingList = facade.getReferencingByLevel(12);

        assertEquals(referencingList.size(), 3);
        facade.deleteOneReferencing(idOfInsertedReferencing);
        facade.deleteOneReferencing(idOfInsertedReferencing2);
        facade.deleteOneReferencing(idOfInsertedReferencing3);
        facade.deleteOneReferencing(idOfInsertedReferencing1);
    }

    /**
     * Test if a referencing can be found thanks to its company id.
     */
    @Test
    void test_findByCompany_OK() {
        ObjectId company = new ObjectId("63a5ce5496a16445da19e223");
        ObjectId company2 = new ObjectId("63a5ce5496a16445da19e224");
        referencing.setCompany(company);
        ObjectId idOfInsertedReferencing = facade.insertOneReferencing(referencing);
        referencing.setCompany(company2);
        ObjectId idOfInsertedReferencing2 = facade.insertOneReferencing(referencing);
        ObjectId idOfInsertedReferencing3 = facade.insertOneReferencing(referencing);

        List<Referencing> referencingList = facade.getReferencingByCompany(company);

        assertEquals(referencingList.size(), 1);
        facade.deleteOneReferencing(idOfInsertedReferencing);
        facade.deleteOneReferencing(idOfInsertedReferencing2);
        facade.deleteOneReferencing(idOfInsertedReferencing3);
    }
}
