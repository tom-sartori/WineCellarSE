package persistence.dao.partner;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.partner.Partner;
import org.bson.conversions.Bson;

/// TODO : Comments.
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
		return Updates.combine(
				Updates.set("name", entity.getName()),
				Updates.set("link", entity.getLink())
		);
	}
}
