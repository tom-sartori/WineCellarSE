package persistence.dao.partner;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.AbstractDao;
import persistence.entity.partner.Partner;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class PartnerDao extends AbstractDao<Partner> {

	private static PartnerDao instance;

	private PartnerDao() { }

	public static PartnerDao getInstance() {
		if (instance == null) {
			instance = new PartnerDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.PARTNER;
	}

	@Override
	protected Class<Partner> getEntityClass() {
		return Partner.class;
	}

	@Override
	protected Bson getSetOnUpdate(Partner entity) {
		List<Bson> updateList = new ArrayList<>();
		updateList.add(Updates.set("name", entity.getName()));
		updateList.add(Updates.set("type", entity.getType()));
		updateList.add(Updates.set("link", entity.getLink()));
		updateList.add(Updates.set("address", entity.getAddress()));
		if (entity.getDescription() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("description", entity.getDescription()));
		}
		if (entity.getPhone() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("phone", entity.getPhone()));
		}
		if (entity.getLogo() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("logo", entity.getLogo()));
		}

		Bson combine = combine(updateList);


//		Bson combine = combine(
//				Updates.set("name", entity.getName()),
//				Updates.set("type", entity.getType()),
//				Updates.set("link", entity.getLink()),
//				Updates.set("address", entity.getAddress()),
//				Updates.set("description", entity.getDescription()),
//				Updates.set("phone", entity.getPhone()),
//				Updates.set("logo", entity.getLogo())
//		);

		return combine;
	}
}
