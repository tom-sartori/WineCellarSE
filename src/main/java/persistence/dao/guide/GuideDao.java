package persistence.dao.guide;
import com.mongodb.client.model.Updates;
import constant.CollectionNames;
import persistence.dao.AbstractDao;
import persistence.entity.guide.Guide;
import org.bson.conversions.Bson;

/// TODO : Comments.
public class GuideDao extends AbstractDao<Guide> {

    private static GuideDao instance;

    private GuideDao() { }

    public static GuideDao getInstance() {
        if (instance == null) {
            instance = new GuideDao();
        }
        return instance;
    }

    @Override
    protected String getCollectionName() {
        return CollectionNames.GUIDE;
    }

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
