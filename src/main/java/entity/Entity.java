package entity;

public interface Entity<T extends Entity<T>> extends Comparable<T> {

	/// TODO : Hooks on find, create, update, delete.
}
