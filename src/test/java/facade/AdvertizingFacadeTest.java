package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.advertizing.Advertizing;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdvertizingFacadeTest {

	private final AdvertizingFacade facade = AdvertizingFacade.getInstance();
	private Advertizing advertizing;

	@BeforeEach
	void init() {
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY,15);
		Date debut = cal.getTime();

		Calendar cal2 = Calendar.getInstance();
		cal2.set(2022, Calendar.JANUARY,17);
		Date fin = cal2.getTime();
		advertizing = new Advertizing("Christmas Kubavin", "Une bouteille à -50% pour Noël chez Kubavin !", "http blabla", " http link", debut, fin, 10);
	}

	@Test
	void test_create_OK() {
		ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
		double priceShouldBeOverridden = 1.0;
		advertizing.setId(idShouldBeOverridden);
		advertizing.setPrice(priceShouldBeOverridden);

		ObjectId idReceivedAfterCreation = facade.insertOneAdvertizing(advertizing);

		assertNotNull(idReceivedAfterCreation);
		assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

		Advertizing receivedAdvertizing = facade.getOneAdvertizing(idReceivedAfterCreation);
		double priceAfterCreation = receivedAdvertizing.getPrice();
		assertEquals(advertizing.getName(), receivedAdvertizing.getName());
		assertEquals(advertizing.getLink(), receivedAdvertizing.getLink());
		assertNotEquals(priceAfterCreation, priceShouldBeOverridden);
	}

	@Test
	void test_findAll_OK() {
		int initialNumberOfAdvertizings = facade.getAdvertizingList().size();

		ObjectId idOfInsertedAdvertizing = facade.insertOneAdvertizing(advertizing);
		facade.insertOneAdvertizing(advertizing);
		facade.insertOneAdvertizing(advertizing);

		List<Advertizing> receivedAdvertizingList = facade.getAdvertizingList();

		assertEquals(3 + initialNumberOfAdvertizings, receivedAdvertizingList.size());
		assertTrue(receivedAdvertizingList.contains(facade.getOneAdvertizing(idOfInsertedAdvertizing)));
	}

	@Test
	void test_findOne_OK() {
		ObjectId idOfInsertedAdvertizing = facade.insertOneAdvertizing(advertizing);

		Advertizing receivedAdvertizing = facade.getOneAdvertizing(idOfInsertedAdvertizing);

		assertEquals(advertizing.getName(), receivedAdvertizing.getName());
		assertEquals(advertizing.getDescription(), receivedAdvertizing.getDescription());
		assertEquals(advertizing.getUrl(), receivedAdvertizing.getUrl());
		assertEquals(advertizing.getLink(), receivedAdvertizing.getLink());
		assertEquals(advertizing.getStartDate(), receivedAdvertizing.getStartDate());
		assertEquals(advertizing.getEndDate(), receivedAdvertizing.getEndDate());
		assertEquals(advertizing.getNbViews(), receivedAdvertizing.getNbViews());
		assertEquals(advertizing.getPrice(), receivedAdvertizing.getPrice());
		assertEquals(advertizing.isActive(), receivedAdvertizing.isActive());
		assertEquals(advertizing.isPayed(), receivedAdvertizing.isPayed());
	}

	@Test
	void test_update_OK() {
		ObjectId idOfInsertedAdvertizing = facade.insertOneAdvertizing(advertizing);
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY,18);
		Date debut = cal.getTime();

		Calendar cal2 = Calendar.getInstance();
		cal2.set(2022, Calendar.JANUARY,19);
		Date fin = cal2.getTime();

		Advertizing receivedAdvertizing = facade.getOneAdvertizing(idOfInsertedAdvertizing);
		receivedAdvertizing.setName("Christmas Kubavin 2");
		receivedAdvertizing.setDescription("Desc 2");
		receivedAdvertizing.setUrl("https url 2");
		receivedAdvertizing.setLink("https link 2");
		receivedAdvertizing.setStartDate(debut);
		receivedAdvertizing.setEndDate(fin);
		receivedAdvertizing.setNbViews(10000);
		receivedAdvertizing.setPrice(0.0);

		facade.updateOneAdvertizing(idOfInsertedAdvertizing, receivedAdvertizing);

		Advertizing updatedAdvertizing = facade.getOneAdvertizing(idOfInsertedAdvertizing);

		assertEquals(receivedAdvertizing.getName(), updatedAdvertizing.getName());
		assertEquals(receivedAdvertizing.getDescription(), updatedAdvertizing.getDescription());
		assertEquals(receivedAdvertizing.getUrl(), updatedAdvertizing.getUrl());
		assertEquals(receivedAdvertizing.getLink(), updatedAdvertizing.getLink());
		assertEquals(receivedAdvertizing.getStartDate(), updatedAdvertizing.getStartDate());
		assertEquals(receivedAdvertizing.getEndDate(), updatedAdvertizing.getEndDate());
	}

	@Test
	void test_delete_OK() {
		ObjectId advertizingIdInserted = facade.insertOneAdvertizing(advertizing);

		List<Advertizing> advertizingList = facade.getAdvertizingList();
		int initialNumberOfAdvertizing = advertizingList.size();
		assertTrue(advertizingList.contains(facade.getOneAdvertizing(advertizingIdInserted)));

		assertTrue(facade.deleteOneAdvertizing(advertizingIdInserted));
		assertEquals(initialNumberOfAdvertizing - 1, facade.getAdvertizingList().size());
	}

	@Test
	void test_pay_and_validate_OK() {
		ObjectId advertizingIdInserted = facade.insertOneAdvertizing(advertizing);
		Advertizing receivedAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		assertFalse(advertizing.isPayed());
		assertFalse(advertizing.isActive());
		facade.payOneAdvertizing(advertizingIdInserted);
		Advertizing updatedPayedAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		assertTrue(updatedPayedAdvertizing.isPayed());
		facade.validateAdvertizing(advertizingIdInserted);
		Advertizing updatedValidateAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		assertTrue(updatedValidateAdvertizing.isActive());
	}

	@Test
	void test_renew_OK() {
		ObjectId advertizingIdInserted = facade.insertOneAdvertizing(advertizing);
		Advertizing receivedAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY,30);
		Date newEnd = cal.getTime();
		receivedAdvertizing.setEndDate(newEnd);
		facade.updateOneAdvertizing(advertizingIdInserted, receivedAdvertizing);

		Advertizing updatedAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		assertEquals(updatedAdvertizing.getEndDate(), newEnd);
	}

	@Test
	void test_addView_OK() {
		ObjectId advertizingIdInserted = facade.insertOneAdvertizing(advertizing);
		Advertizing receivedAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		int ancienNb = receivedAdvertizing.getNbViews();
		facade.addView(advertizingIdInserted);

		Advertizing updatedAdvertizing = facade.getOneAdvertizing(advertizingIdInserted);
		assertEquals(updatedAdvertizing.getNbViews(), ancienNb+1);
	}
}
