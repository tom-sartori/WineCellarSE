package persistence.dao.mongodb.referencing;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.referencing.Referencing;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class ReferencingMongoDao extends AbstractMongoDao<Referencing> {

	private static ReferencingMongoDao instance;

	private ReferencingMongoDao() { }

	public static ReferencingMongoDao getInstance() {
		if (instance == null) {
			instance = new ReferencingMongoDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.REFERENCING;
	}

	@Override
	protected Class<Referencing> getEntityClass() {
		return Referencing.class;
	}

	@Override
	protected Bson getSetOnUpdate(Referencing entity) {
		List<Bson> updateList = new ArrayList<>();

		updateList.add(Updates.set("price", entity.getPrice()));
		updateList.add(Updates.set("paymentDate", entity.getPaymentDate()));
		updateList.add(Updates.set("startDate", entity.getStartDate()));
		updateList.add(Updates.set("expirationDate", entity.getExpirationDate()));
		updateList.add(Updates.set("id", entity.getId()));
		updateList.add(Updates.set("status", entity.getStatus()));
		updateList.add(Updates.set("importanceLevel", entity.getImportanceLevel()));
		updateList.add(Updates.set("company", entity.getCompany()));

		return combine(updateList);
	}
}
