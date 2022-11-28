package controller;

import entity.Entity;
import model.ModelInterface;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;

//TODO interface
public abstract class Controller <T extends Entity<T>> {

    /**
     *
     * @param id
     * @return
     */
    public T findOne(ObjectId id) {
        T first = getModel().findOne(id);
        first.handleOnFind();
        return first;
    }

    /**
     *
     * @return
     */
    public List<T> findAll() {
        List<T> all = getModel().findAll();
        all.forEach(Entity::handleOnFind);
        Collections.sort(all);
        return all;
    }

    /**
     *
     * @param entity
     * @return
     */
    public ObjectId insertOne(T entity) {
        ObjectId id = getModel().insertOne(entity);
        entity.handleOnCreate();
        return id;
    }

    //TODO make update function
//    public void updateOne(ObjectId id, T t){
//        getModel().updateOne(id,t);
//        t.handleOnUpdate();
//    }

    /**
     *
     * @param id
     * @return
     */
    public long deleteOne(ObjectId id) {
        T entityToDelete = findOne(id);
        entityToDelete.handleOnDelete();
        return getModel().deleteOne(id);
    }

    protected abstract ModelInterface<T> getModel();
}
