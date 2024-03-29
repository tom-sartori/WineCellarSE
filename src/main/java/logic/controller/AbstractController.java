package logic.controller;

import org.bson.types.ObjectId;
import persistence.dao.Dao;
import persistence.entity.Entity;

import java.util.Collections;
import java.util.List;


/**
 * Abstract controller used to define generic methods used by controllers.
 * If needed, the controller can override the methods.
 *
 * @param <T> The entity type which the controller manage.
 */
public abstract class AbstractController<T extends Entity<T>> implements Controller<T> {

    /**
     * Generic implementation to insert one entity of the parametrized type.
     *
     * @param entity The entity to insert.
     * @return The id of the inserted entity.
     */
    @Override
    public ObjectId insertOne(T entity) {
        entity.handleOnCreate();
        return getDao().insertOne(entity);
    }

    /**
     * Generic implementation to find one entity of the parametrized type by its id.
     *
     * @param id The id of the entity to find.
     * @return The entity found or null.
     */
    @Override
    public T findOne(ObjectId id) {
        T entity = getDao().findOne(id);
        entity.handleOnFind();
        return entity;
    }

    /**
     * Generic implementation to find all entities of the parametrized type.
     *
     * @return The list of all entities.
     */
    @Override
    public List<T> findAll() {
        List<T> all = getDao().findAll();
        all.forEach(Entity::handleOnFind);
        Collections.sort(all);
        return all;
    }
    /**
     * Generic implementation to update one entity of the parametrized type.
     *
     * @param id The id of the entity to update.
     * @param t The new entity.
     * @return true if the entity has been updated, false otherwise.
     */
    @Override
    public boolean updateOne(ObjectId id, T t){
        boolean isUpdated = getDao().updateOne(id, t);
        t.handleOnUpdate();
        return isUpdated;
    }

    /**
     * Generic implementation to delete one entity of the parametrized type.
     *
     * @param id The id of the entity to delete.
     * @return true if the entity has been deleted, false otherwise.
     */
    @Override
    public boolean deleteOne(ObjectId id) {
        T entity = findOne(id);
        entity.handleOnDelete();
        return getDao().deleteOne(id);
    }

    /**
     * Get the DAO used by the specific controller in order to call its DAO methods.
     * Should be the DAO which manage the entity managed by the controller.
     *
     * @return The DAO used by the specific controller.
     */
    protected abstract Dao<T> getDao();
}
