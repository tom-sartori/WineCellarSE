package persistence.entity.cellar;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.List;
import java.util.Objects;

public class EmplacementBottle implements Entity<EmplacementBottle> {

    private ObjectId id;
    private List<Point> pointList;
    private List<BottleQuantity> bottleList;

    public EmplacementBottle() {
    }

    public EmplacementBottle(List<Point> pointList, List<BottleQuantity> bottleList) {
        this.pointList = pointList;
        this.bottleList = bottleList;
    }

    @Override
    public void handleOnCreate() {
        this.setId(null);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public List<BottleQuantity> getBottleList() {
        return bottleList;
    }

    public void setBottleList(List<BottleQuantity> bottleList) {
        this.bottleList = bottleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmplacementBottle that = (EmplacementBottle) o;
        return Objects.equals(id, that.id) && Objects.equals(pointList, that.pointList) && Objects.equals(bottleList, that.bottleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pointList, bottleList);
    }

    @Override
    public int compareTo(EmplacementBottle o) {
        return getId().compareTo(o.getId());
    }
}
