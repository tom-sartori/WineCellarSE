package model.partner;

import constant.CollectionNames;
import entity.partner.Partner;
import model.Model;

public class PartnerModel extends Model<Partner> {
	@Override
	protected String getCollectionName() {
		return CollectionNames.PARTNER;
	}

	@Override
	protected Class<Partner> getEntityClass() {
		return Partner.class;
	}
}
