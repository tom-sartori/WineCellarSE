package persistence.entity.rate;

import exception.BadValueException;
import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Rate implements Entity<Rate> {

	private ObjectId id;
	private ObjectId ownerRef;
	private ObjectId subjectRef;
	private int rate;
	private String comment;
	private boolean isModified;
	private Date lastModified;

	public Rate(){}

	public Rate(ObjectId ownerRef, ObjectId subjectRef, int rate, String comment, boolean isModified, Date lastModified) {
		this.ownerRef = ownerRef;
		this.subjectRef = subjectRef;
		this.rate = rate;
		this.comment = comment;
		this.isModified = isModified;
		this.lastModified = lastModified;
	}

	@Override
	public void handleOnCreate() throws BadValueException {
		this.id = null;
		if (rate < 0 || rate > 5) {
			throw new BadValueException("la note choisit doit être entre 0 et 5");
		}

	}

	@Override
	public void handleOnUpdate() throws BadValueException{
		if (rate < 0 || rate > 5) {
			throw new BadValueException("la note choisi doit être entre 0 et 5");
		}
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getOwnerRef() {
		return ownerRef;
	}

	public void setOwnerRef(ObjectId ownerRef) {
		this.ownerRef = ownerRef;
	}

	public ObjectId getSubjectRef() {
		return subjectRef;
	}

	public void setSubjectRef(ObjectId subjectRef) {
		this.subjectRef = subjectRef;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean modified) {
		isModified = modified;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Rate rate1 = (Rate) o;
		return isModified == rate1.isModified && Objects.equals(id, rate1.id) && Objects.equals(rate, rate1.rate) && Objects.equals(lastModified, rate1.lastModified);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, rate, isModified, lastModified);
	}

	@Override
	public int compareTo(Rate o) {
		return lastModified.compareTo(o.lastModified);
	}

	@Override
	public String toString() {
		return "Rate{" +
				"rate='" + rate + '\'' +
				", comment='" + comment + '\'' +
				", isModified=" + isModified +
				", lastModified=" + lastModified +
				'}';
	}
}
