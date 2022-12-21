package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.advertising.Advertising;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisingFacadeTest {

	private final AdvertisingFacade facade = AdvertisingFacade.getInstance();
	private Advertising advertising;

	@BeforeEach
	void init() {
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY,15);
		Date debut = cal.getTime();

		Calendar cal2 = Calendar.getInstance();
		cal2.set(2022, Calendar.JANUARY,17);
		Date fin = cal2.getTime();
		advertising = new Advertising("Christmas Kubavin", "Une bouteille à -50% pour Noël chez Kubavin !", "http blabla", " http link", debut, fin, 10);
	}

	@Test
	void test_create_OK() {
		ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
		double priceShouldBeOverridden = 1.0;
		advertising.setId(idShouldBeOverridden);
		advertising.setPrice(priceShouldBeOverridden);

		ObjectId idReceivedAfterCreation = facade.insertOneAdvertising(advertising);

		assertNotNull(idReceivedAfterCreation);
		assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

		Advertising receivedAdvertising = facade.getOneAdvertising(idReceivedAfterCreation);
		double priceAfterCreation = receivedAdvertising.getPrice();
		assertEquals(advertising.getName(), receivedAdvertising.getName());
		assertEquals(advertising.getLink(), receivedAdvertising.getLink());
		assertNotEquals(priceAfterCreation, priceShouldBeOverridden);
	}

	@Test
	void test_findAll_OK() {
		int initialNumberOfAdvertisings = facade.getAdvertisingList().size();

		ObjectId idOfInsertedAdvertising = facade.insertOneAdvertising(advertising);
		facade.insertOneAdvertising(advertising);
		facade.insertOneAdvertising(advertising);

		List<Advertising> receivedAdvertisingList = facade.getAdvertisingList();

		assertEquals(3 + initialNumberOfAdvertisings, receivedAdvertisingList.size());
		assertTrue(receivedAdvertisingList.contains(facade.getOneAdvertising(idOfInsertedAdvertising)));
	}

	@Test
	void test_findOne_OK() {
		ObjectId idOfInsertedAdvertising = facade.insertOneAdvertising(advertising);

		Advertising receivedAdvertising = facade.getOneAdvertising(idOfInsertedAdvertising);

		assertEquals(advertising.getName(), receivedAdvertising.getName());
		assertEquals(advertising.getDescription(), receivedAdvertising.getDescription());
		assertEquals(advertising.getUrl(), receivedAdvertising.getUrl());
		assertEquals(advertising.getLink(), receivedAdvertising.getLink());
		assertEquals(advertising.getStartDate(), receivedAdvertising.getStartDate());
		assertEquals(advertising.getEndDate(), receivedAdvertising.getEndDate());
		assertEquals(advertising.getNbViews(), receivedAdvertising.getNbViews());
		assertEquals(advertising.getPrice(), receivedAdvertising.getPrice());
		assertEquals(advertising.isActive(), receivedAdvertising.isActive());
		assertEquals(advertising.isPayed(), receivedAdvertising.isPayed());
	}

	@Test
	void test_update_OK() {
		ObjectId idOfInsertedAdvertising = facade.insertOneAdvertising(advertising);
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY,18);
		Date debut = cal.getTime();

		Calendar cal2 = Calendar.getInstance();
		cal2.set(2022, Calendar.JANUARY,19);
		Date fin = cal2.getTime();

		Advertising receivedAdvertising = facade.getOneAdvertising(idOfInsertedAdvertising);
		receivedAdvertising.setName("Christmas Kubavin 2");
		receivedAdvertising.setDescription("Desc 2");
		receivedAdvertising.setUrl("https url 2");
		receivedAdvertising.setLink("https link 2");
		receivedAdvertising.setStartDate(debut);
		receivedAdvertising.setEndDate(fin);
		receivedAdvertising.setNbViews(10000);
		receivedAdvertising.setPrice(0.0);

		facade.updateOneAdvertising(idOfInsertedAdvertising, receivedAdvertising);

		Advertising updatedAdvertising = facade.getOneAdvertising(idOfInsertedAdvertising);

		assertEquals(receivedAdvertising.getName(), updatedAdvertising.getName());
		assertEquals(receivedAdvertising.getDescription(), updatedAdvertising.getDescription());
		assertEquals(receivedAdvertising.getUrl(), updatedAdvertising.getUrl());
		assertEquals(receivedAdvertising.getLink(), updatedAdvertising.getLink());
		assertEquals(receivedAdvertising.getStartDate(), updatedAdvertising.getStartDate());
		assertEquals(receivedAdvertising.getEndDate(), updatedAdvertising.getEndDate());
	}

	@Test
	void test_delete_OK() {
		ObjectId advertisingIdInserted = facade.insertOneAdvertising(advertising);

		List<Advertising> advertisingList = facade.getAdvertisingList();
		int initialNumberOfAdvertising = advertisingList.size();
		assertTrue(advertisingList.contains(facade.getOneAdvertising(advertisingIdInserted)));

		assertTrue(facade.deleteOneAdvertising(advertisingIdInserted));
		assertEquals(initialNumberOfAdvertising - 1, facade.getAdvertisingList().size());
	}

	@Test
	void test_pay_and_validate_OK() {
		ObjectId advertisingIdInserted = facade.insertOneAdvertising(advertising);
		Advertising receivedAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		assertFalse(advertising.isPayed());
		assertFalse(advertising.isActive());
		facade.payOneAdvertising(advertisingIdInserted);
		Advertising updatedPayedAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		assertTrue(updatedPayedAdvertising.isPayed());
		facade.validateAdvertising(advertisingIdInserted);
		Advertising updatedValidateAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		assertTrue(updatedValidateAdvertising.isActive());
	}

	@Test
	void test_renew_OK() {
		ObjectId advertisingIdInserted = facade.insertOneAdvertising(advertising);
		Advertising receivedAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY,30);
		Date newEnd = cal.getTime();
		receivedAdvertising.setEndDate(newEnd);
		facade.updateOneAdvertising(advertisingIdInserted, receivedAdvertising);

		Advertising updatedAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		assertEquals(updatedAdvertising.getEndDate(), newEnd);
	}

	@Test
	void test_addView_OK() {
		ObjectId advertisingIdInserted = facade.insertOneAdvertising(advertising);
		Advertising receivedAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		int ancienNb = receivedAdvertising.getNbViews();
		facade.addView(advertisingIdInserted);

		Advertising updatedAdvertising = facade.getOneAdvertising(advertisingIdInserted);
		assertEquals(updatedAdvertising.getNbViews(), ancienNb+1);
	}
}
