package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.partner.Partner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartnerFacadeTest {

	private final FacadeInterface facade = Facade.getInstance();
	private Partner partner;

	@BeforeEach
	void init() {
		partner = new Partner("Kubavin", "Shop", "https://kuba-vin.fr/", "20 rue du vin, 68000, Colmar");
	}

	@Test
	void test_create_OK() {
		ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
		partner.setId(idShouldBeOverridden);

		ObjectId idReceivedAfterCreation = facade.insertOnePartner(partner);

		assertNotNull(idReceivedAfterCreation);
		assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

		Partner receivedPartner = facade.getOnePartner(idReceivedAfterCreation);
		assertEquals(partner.getName(), receivedPartner.getName());
		assertEquals(partner.getLink(), receivedPartner.getLink());
	}

	@Test
	void test_findAll_OK() {
		int initialNumberOfPartners = facade.getPartnerList().size();

		ObjectId idOfInsertedPartner = facade.insertOnePartner(partner);
		facade.insertOnePartner(partner);
		facade.insertOnePartner(partner);

		List<Partner> receivedPartnerList = facade.getPartnerList();

		assertEquals(3 + initialNumberOfPartners, receivedPartnerList.size());
		assertTrue(receivedPartnerList.contains(facade.getOnePartner(idOfInsertedPartner)));
	}

	@Test
	void test_findOne_OK() {
		ObjectId idOfInsertedPartner = facade.insertOnePartner(partner);

		Partner receivedPartner = facade.getOnePartner(idOfInsertedPartner);

		assertEquals(partner.getName(), receivedPartner.getName());
		assertEquals(partner.getType(), receivedPartner.getType());
		assertEquals(partner.getLink(), receivedPartner.getLink());
		assertEquals(partner.getAddress(), receivedPartner.getAddress());
	}

	@Test
	void test_update_OK() {
		ObjectId idOfInsertedPartner = facade.insertOnePartner(partner);

		Partner receivedPartner = facade.getOnePartner(idOfInsertedPartner);
		receivedPartner.setName("Kubavin2");
		receivedPartner.setType("Shop2");
		receivedPartner.setLink("https://kuba-vin2.fr/");
		receivedPartner.setAddress("20 rue du vin, 68000, Colmar2");

		facade.updateOnePartner(receivedPartner.getId(), receivedPartner);

		Partner updatedPartner = facade.getOnePartner(idOfInsertedPartner);

		assertEquals(receivedPartner.getName(), updatedPartner.getName());
		assertEquals(receivedPartner.getType(), updatedPartner.getType());
		assertEquals(receivedPartner.getLink(), updatedPartner.getLink());
		assertEquals(receivedPartner.getAddress(), updatedPartner.getAddress());
	}

	@Test
	void test_delete_OK() {
		ObjectId partnerIdInserted = facade.insertOnePartner(partner);

		List<Partner> partnerList = facade.getPartnerList();
		int initialNumberOfPartners = partnerList.size();
		assertTrue(partnerList.contains(facade.getOnePartner(partnerIdInserted)));

		assertTrue(facade.deleteOnePartner(partnerIdInserted));
		assertEquals(initialNumberOfPartners - 1, facade.getPartnerList().size());
	}
}
