package model;

import entity.Entity;
import org.bson.types.ObjectId;

import java.util.List;

/// TODO : Comments.
public interface ModelInterface<T extends Entity<T>> {

	T findOne(ObjectId id);
	List<T> findAll();
	ObjectId insertOne(T t);
	long updateOne(ObjectId id, T newEntity);
	long deleteOne(ObjectId id);
}
