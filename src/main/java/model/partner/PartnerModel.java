package model.partner;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import entity.partner.Partner;
import model.Model;
import org.bson.conversions.Bson;

/// TODO : Comments.
public class PartnerModel extends Model<Partner> {

	private static PartnerModel instance;

	private PartnerModel() { }

	public static PartnerModel getInstance() {
		if (instance == null) {
			instance = new PartnerModel();
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
