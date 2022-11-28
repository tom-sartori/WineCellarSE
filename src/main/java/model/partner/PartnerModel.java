package model.partner;

import constant.CollectionNames;
import entity.partner.Partner;
import model.Model;

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
}
