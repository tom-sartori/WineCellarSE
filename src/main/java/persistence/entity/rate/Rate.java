package persistence.entity.rate;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Rate implements Entity<Rate> {

	private ObjectId id;
	private String rate;
	private String comment;
	private boolean isModified;
	private Date lastModified;


	public Rate() { }

	public Rate(String rate, String comment, boolean isModified, Date lastModified) {
		this.rate = rate;
		this.comment = comment;
		this.isModified = isModified;
		this.lastModified = lastModified;
	}

	@Override
	public void handleOnCreate() {
		this.id = null;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
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
		return rate.compareTo(o.rate);
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
