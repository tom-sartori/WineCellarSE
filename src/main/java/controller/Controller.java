package controller;

import entity.Entity;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Controller interface used to define methods implemented by controllers.
 * The controller manage the business logic between the model and the facade.
 *
 * @param <T> The entity type which the controller manage.
 */
public interface Controller<T extends Entity<T>> {

	/**
	 * Insert one entity of the parametrized type.
	 *
	 * @param entity The entity to insert.
	 * @return The id of the inserted entity.
	 */
	ObjectId insertOne(T entity);

	/**
	 * Find one entity of the parametrized type by its id.
	 *
	 * @param id The id of the entity to find.
	 * @return The entity found.
	 */
	T findOne(ObjectId id);

	/**
	 * Find all entities of the parametrized type.
	 *
	 * @return The list of all entities.
	 */
	List<T> findAll();

	/**
	 * Update one entity of the parametrized type.
	 *
	 * @param id The id of the entity to update.
	 * @param t The new entity.
	 * @return The number of updated entities.
	 */
	long updateOne(ObjectId id, T t);

	/**
	 * Delete one entity of the parametrized type.
	 *
	 * @param id The id of the entity to delete.
	 * @return The number of deleted entities.
	 */
	long deleteOne(ObjectId id);
}
