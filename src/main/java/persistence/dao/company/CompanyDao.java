package persistence.dao.company;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.AbstractDao;
import persistence.entity.company.Company;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class CompanyDao extends AbstractDao<Company> {

	private static CompanyDao instance;

	private CompanyDao() { }

	public static CompanyDao getInstance() {
		if (instance == null) {
			instance = new CompanyDao();
		}
		return instance;
	}

	// TODO FINDALLACCESSIBLECOMPANIES



	@Override
	protected String getCollectionName() {
		return CollectionNames.COMPANY;
	}

	@Override
	protected Class<Company> getEntityClass() {
		return Company.class;
	}

	@Override
	protected Bson getSetOnUpdate(Company entity) {
		List<Bson> updateList = new ArrayList<>();

		updateList.add(Updates.set("name", entity.getName()));
		updateList.add(Updates.set("type", entity.getType()));
		updateList.add(Updates.set("address", entity.getAddress()));
		updateList.add(Updates.set("accessible", entity.isAccessible()));
		updateList.add(Updates.set("masterManager", entity.getMasterManager()));
		updateList.add(Updates.set("managerList", entity.getManagerList()));
		updateList.add(Updates.set("cellar", entity.getCellar()));

		// Nullable fields

		if (entity.getPhoneNumber() != null) {
			updateList.add(Updates.set("phoneNumber", entity.getPhoneNumber()));
		}

		if (entity.getDescription() != null) {
			updateList.add(Updates.set("description", entity.getDescription()));
		}

		if (entity.getWebsiteLink() != null) {
			updateList.add(Updates.set("websiteLink", entity.getWebsiteLink()));
		}

		if (entity.getLogoLink() != null) {
			updateList.add(Updates.set("logoLink", entity.getLogoLink()));
		}

		return combine(updateList);
	}
}
