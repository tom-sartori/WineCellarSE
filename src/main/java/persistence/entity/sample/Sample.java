package persistence.entity.sample;

import org.bson.types.ObjectId;
import persistence.entity.Entity;


public class Sample implements Entity<Sample> {

	/// TODO : Set the fields that you need for your entity.
	public ObjectId id;
	public String name;
//	@Nullable
//	public String description;

	public Sample() { }

	/// TODO : You can create another constructor if you need it for tests. The constructor maye be without the entity's id.


	/// TODO : If you need it, override hooks from the interface Entity.


	/// TODO : Generate all getters and setters.


	/// TODO : Generate equals and hashCode methods.


	/// TODO : Update compareTo method if needed.
	@Override
	public int compareTo(Sample o) {
		return name.compareTo(o.name);
	}
}
