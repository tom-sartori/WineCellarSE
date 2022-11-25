package model.partner;

import entity.partner.Partner;
import model.Model;

public class PartnerModel extends Model<Partner> {
	@Override
	protected String getCollectionName() {
		/// TODO : Constant file for collection names.
		return "partners";
	}

	@Override
	protected Class<Partner> getEntityClass() {
		return Partner.class;
	}
}
