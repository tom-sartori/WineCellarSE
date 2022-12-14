package persistence.entity.cellar;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Objects;

public class Point implements Entity<Point> {

    private ObjectId id;
    private int x;
    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void handleOnCreate() {
        setId(null);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y && Objects.equals(id, point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }

    @Override
    public int compareTo(Point o) {
        return 0;
    }
}
