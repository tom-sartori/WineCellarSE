package persistence.dao.guide;
import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.guide.Guide;
import org.bson.conversions.Bson;

public class GuideDao extends AbstractDao<Guide> {

    private static GuideDao instance;

    /**
     * Singleton instance.
     */
    private GuideDao() { }

    /**
     * Get the singleton instance of the DAO.
     *
     * @return The singleton instance.
     */
    public static GuideDao getInstance() {
        if (instance == null) {
            instance = new GuideDao();
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
                Updates.set("sectionList", entity.getSectionList())
        );
    }


}




