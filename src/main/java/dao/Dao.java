package dao;

import entity.Entity;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

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
	 * Should return an empty optional if the entity is not found.
	 *
	 * @param id The id of the entity to find.
	 * @return The entity found or an empty optional.
	 */
	Optional<T> findOne(ObjectId id);

	/**
	 * Update one entity of the parametrized type.
	 *
	 * @param id The id of the entity to update.
	 * @param newEntity The new entity.
	 * @return The number of updated entities.
	 */
	long updateOne(ObjectId id, T newEntity);

	/**
	 * Delete one entity of the parametrized type.
	 *
	 * @param id The id of the entity to delete.
	 * @return The number of deleted entities.
	 */
	long deleteOne(ObjectId id);
}
