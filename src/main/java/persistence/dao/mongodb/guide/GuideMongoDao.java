package persistence.dao.mongodb.guide;

import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import org.bson.conversions.Bson;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.guide.Guide;

public class GuideMongoDao extends AbstractMongoDao<Guide> {

    private static GuideMongoDao instance;

    /**
     * Singleton instance.
     */
    private GuideMongoDao() { }

    /**
     * Get the singleton instance of the DAO.
     *
     * @return The singleton instance.
     */
    public static GuideMongoDao getInstance() {
        if (instance == null) {
            instance = new GuideMongoDao();
        }
        return instance;
    }

    /**
     * @return the constant name of the specific Collection (Notification).
     */
    @Override
    protected String getCollectionName() {
        return CollectionNames.GUIDE;
    }

    /**
     * @return the class of the specific Entity (Notification).
     */
    @Override
    protected Class<Guide> getEntityClass() {
        return Guide.class;
    }

    @Override
    protected Bson getSetOnUpdate(Guide entity) {
        return Updates.combine(
                Updates.set("title", entity.getTitle()),
                Updates.set("description", entity.getDescription()),
                Updates.set("sectionList", entity.getSectionList())
        );
    }


}




