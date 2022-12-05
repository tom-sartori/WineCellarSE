package persistence.entity;

public interface Entity<T extends Entity<T>> extends Comparable<T> {

    /**
     *  Hook method optionally used when the entity is found
     */
    default void handleOnFind() { }

    /**
     *  Hook method optionally used when the entity is created
     */
    default void handleOnCreate() { }

    /**
     *  Hook method optionally used when the entity is updated
     */
    default void handleOnUpdate() { }

    /**
     *  Hook method optionally used when the entity is deleted
     */
    default void handleOnDelete() { }
}
