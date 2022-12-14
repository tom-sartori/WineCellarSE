package persistence.entity.cellar;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.List;
import java.util.Objects;

public class Wall implements Entity<Wall> {

    private ObjectId id;
    private String image;
    private List<EmplacementBottle> emplacementBottleMap;
    private String name;

    public Wall() {
    }

    public Wall(ObjectId id, String image, List<EmplacementBottle> emplacementBottleMap, String name) {
        this.id = id;
        this.image = image;
        this.emplacementBottleMap = emplacementBottleMap;
        this.name = name;
    }

    public Wall(String image, List<EmplacementBottle> emplacementBottleMap, String name) {
        this.image = image;
        this.emplacementBottleMap = emplacementBottleMap;
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<EmplacementBottle> getEmplacementBottleMap() {
        return emplacementBottleMap;
    }

    public void setEmplacementBottleMap(List<EmplacementBottle> emplacementBottleMap) {
        this.emplacementBottleMap = emplacementBottleMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(id, wall.id) && Objects.equals(image, wall.image) && Objects.equals(emplacementBottleMap, wall.emplacementBottleMap) && Objects.equals(name, wall.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, emplacementBottleMap, name);
    }

    @Override
    public int compareTo(Wall o) {
        return getName().compareTo(o.getName());
    }
}
