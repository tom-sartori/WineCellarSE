package persistence.entity.cellar;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.List;
import java.util.Objects;

public class Cellar implements Entity<Cellar> {

    private ObjectId id;
    private String name;
    private boolean isPublic;
    private List<ObjectId> readers;
    private List<ObjectId> managers;
    private ObjectId ownerRef;
    private List<Wall> walls;

    public Cellar() {
    }

    public Cellar(String name, boolean isPublic, List<ObjectId> readers, List<ObjectId> managers, ObjectId ownerRef, List<Wall> walls) {
        this.name = name;
        this.isPublic = isPublic;
        this.readers = readers;
        this.managers = managers;
        this.ownerRef = ownerRef;
        this.walls = walls;
    }

    /**
     *  The user should not be able to set an id while inserting, the database has to handle it.
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ObjectId getOwnerRef() {
        return ownerRef;
    }

    public void setOwnerRef(ObjectId ownerRef) {
        this.ownerRef = ownerRef;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<ObjectId> getReaders() {
        return readers;
    }

    public void setReaders(List<ObjectId> readers) {
        this.readers = readers;
    }

    public List<ObjectId> getManagers() {
        return managers;
    }

    public void setManagers(List<ObjectId> managers) {
        this.managers = managers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cellar cellar = (Cellar) o;
        return isPublic == cellar.isPublic && Objects.equals(id, cellar.id) && Objects.equals(name, cellar.name) && Objects.equals(readers, cellar.readers) && Objects.equals(managers, cellar.managers) && Objects.equals(ownerRef, cellar.ownerRef) && Objects.equals(walls, cellar.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isPublic, readers, managers, ownerRef, walls);
    }

    @Override
    public int compareTo(Cellar o) {
        return name.compareTo(o.getName());
    }
}
