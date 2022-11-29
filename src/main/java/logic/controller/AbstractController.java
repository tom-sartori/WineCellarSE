package logic.controller;

import persistence.entity.Entity;
import persistence.dao.Dao;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


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
        ObjectId id = getDao().insertOne(entity);
        entity.handleOnCreate();
        return id;
    }

    /**
     * Generic implementation to find one entity of the parametrized type by its id.
     *
     * @param id The id of the entity to find.
     * @return The entity found or an empty optional.
     */
    @Override
    public Optional<T> findOne(ObjectId id) {
        Optional<T> optionalEntity = getDao().findOne(id);
        optionalEntity.ifPresent(Entity::handleOnFind);
        return optionalEntity;
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
     * @return The number of updated entities.
     */
    @Override
    public long updateOne(ObjectId id, T t){
        long nbOfUpdated = getDao().updateOne(id, t);
        t.handleOnUpdate();
        return nbOfUpdated;
    }

    /**
     * Generic implementation to delete one entity of the parametrized type.
     *
     * @param id The id of the entity to delete.
     * @return The number of deleted entities.
     */
    @Override
    public long deleteOne(ObjectId id) {
        Optional<T> optionalEntityToDelete = findOne(id);
        optionalEntityToDelete.ifPresent(Entity::handleOnDelete);
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
