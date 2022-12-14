package persistence.dao.rate;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.rate.Rate;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;

public class RateDao extends AbstractDao<Rate> {

	private static RateDao instance;

	private RateDao() { }

	public static RateDao getInstance() {
		if (instance == null) {
			instance = new RateDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.RATE;
	}

	@Override
	protected Class<Rate> getEntityClass() {
		return Rate.class;
	}

	@Override
	protected Bson getSetOnUpdate(Rate entity) {
		return Updates.combine(
				Updates.set("rate", entity.getRate()),
				Updates.set("comment", entity.getComment()),
				Updates.set("lastModified", entity.getLastModified()),
				Updates.set("modified", entity.isModified())
		);

	}
}
