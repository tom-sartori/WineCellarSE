package persistence.dao.company;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import exception.BadArgumentsException;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
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

	// TODO TEST
	/**
	 * Get the list of companies that are accessible or not depending on parameter
	 *
	 * @param isAccessible true if we want the accessible companies, false if we want the inaccessible companies.
	 *
	 * @return the list of companies.
	 */
	protected List<Company> findAllCompaniesByAccessibility(boolean isAccessible) {
		BsonDocument filter = new BsonDocument();

		filter.append("accessible", new BsonBoolean(isAccessible));

		return super.findAllWithFilter(filter);
	}

	// TODO TEST
	/**
	 * Return the list of companies that are accessible.
	 *
	 * @return The list of accessible companies if any, throws a NotFoundException otherwise.
	 */
	public List<Company> findAllAccessibleCompanies() {
		return findAllCompaniesByAccessibility(true);
	}

	// TODO TEST
	/**
	 * Return the list of companies that are not accessible.
	 *
	 * @return The list of companies that are not accessible if there are any, a NotFoundException is thrown otherwise.
	 */
	public List<Company> findAllUnaccessibleCompanies() {
		return findAllCompaniesByAccessibility(false);
	}

	// TODO TEST
	// TODO SEND NOTIF
	/**
	 * Add a manager to a company.
	 *
	 * @param companyId The id of the company.
	 * @param managerId The id of the manager to add.
	 *
	 * @return The id of the updated company if the manager was added, throws a BadArgumentsException otherwise.
	 * @throws BadArgumentsException if the company or the manager does not exist.
	 */
	public ObjectId addManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
		return addOrRemoveFromSet(companyId, managerId, "managerList", true);
	}

	// TODO TEST
	// TODO SEND NOTIF
	/**
	 * Removes a manager from a company.
	 *
	 * @param companyId The id of the company.
	 * @param managerId The id of the manager.
	 *
	 * @return The id of the company if the manager was removed successfully, else throws a BadArgumentsException.
	 * @throws BadArgumentsException If the company or the manager does not exist.
	 */
	public ObjectId removeManager(ObjectId companyId, ObjectId managerId) throws BadArgumentsException {
		return addOrRemoveFromSet(companyId, managerId, "managerList", false);
	}

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
