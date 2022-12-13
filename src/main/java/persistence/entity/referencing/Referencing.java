package persistence.entity.referencing;

import org.bson.types.ObjectId;
import persistence.entity.Entity;


public class Referencing implements Entity<Referencing> {

	/// TODO : Set the fields that you need for your entity.
	private ObjectId id;
	private String name;
//	@Nullable
//	public String description;

	public Referencing() { }

	/// TODO : You can create another constructor if you need it for tests. The constructor maye be without the entity's id.


	/// TODO : If you need it, override hooks from the interface Entity.


	/// TODO : Generate all getters and setters.


	/// TODO : Generate equals and hashCode methods.


	/// TODO : Update compareTo method if needed.
	@Override
	public int compareTo(Referencing o) {
		return name.compareTo(o.name);
	}
}
