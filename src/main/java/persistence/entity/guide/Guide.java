package persistence.entity.guide;

import persistence.entity.Entity;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class Guide implements Entity<Guide>{

    private ObjectId id;
    private String title;
    private LinkedHashMap<String, List<String>> sectionList;
    private GuideCategory category;
    private Date creationDate;

    public Guide() {
    }

    public Guide(String title, LinkedHashMap<String, List<String>> sectionList, GuideCategory category, Date creationDate) {
        this.title = title;
        this.sectionList = sectionList;
        this.category = category;
        this.creationDate = creationDate;
    }

    /**
     * The user should not be able to set the id. It is automatically generated by the database.
     */
    @Override
    public void handleOnCreate() {
        this.id = null;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedHashMap<String, List<String>> getSectionList() {
        return sectionList;
    }

    public void setSectionList(LinkedHashMap<String, List<String>> sectionList) {
        this.sectionList = sectionList;
    }

    public GuideCategory getCategory() {
        return category;
    }

    public void setCategory(GuideCategory category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guide guide = (Guide) o;
        return Objects.equals(id, guide.id) && Objects.equals(title, guide.title) && Objects.equals(sectionList, guide.sectionList) && category == guide.category && Objects.equals(creationDate, guide.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, sectionList, category, creationDate);
    }

    @Override
    public int compareTo(Guide o) {
        return creationDate.compareTo(o.creationDate);
    }
}
