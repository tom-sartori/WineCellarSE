package persistence.dao;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.List;

/**
 * Data access object pattern which isolate the persistence layer.
 *
 * @param <T> The entity type which the controller manage.
 */
public interface Dao<T extends Entity<T>> {

	/**
	 * Insert one entity of the parametrized type.
	 *
	 * @param entity The entity to insert.
	 * @return The id of the inserted entity.
	 */
	ObjectId insertOne(T entity);

	/**
	 * Find all entities of the parametrized type.
	 *
	 * @return The list of all entities.
	 */
	List<T> findAll();

	/**
	 * Find one entity of the parametrized type by its id.
	 *
	 * @param id The id of the entity to find.
	 * @return The entity found or null;
	 */
	T findOne(ObjectId id);

	/**
	 * Update one entity of the parametrized type.
	 *
	 * @param id The id of the entity to update.
	 * @param newEntity The new entity.
	 * @return true if the entity has been updated, false otherwise.
	 */
	boolean updateOne(ObjectId id, T newEntity);

	/**
	 * Delete one entity of the parametrized type.
	 *
	 * @param id The id of the entity to delete.
	 * @return true if the entity has been deleted, false otherwise.
	 */
	boolean deleteOne(ObjectId id);
}
