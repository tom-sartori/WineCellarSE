package model;

import entity.Entity;
import org.bson.types.ObjectId;

import java.util.List;

public interface ModelInterface<T extends Entity<T>> {

    public T findOne(ObjectId id);
    public List<T> findAll();
    public ObjectId insertOne(T t);
//    public void updateOne(ObjectId id, T t);
    public long deleteOne(ObjectId id);
}
