package persistence.entity.notification;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Notification implements Entity<Notification> {

	private ObjectId id;
	private ObjectId ownerRef;
	private String message;
	private boolean isRead;
	private Date date;

	public Notification() { }

	public Notification(ObjectId ownerRef, String message, boolean isRead, Date date) {
		this.ownerRef = ownerRef;
		this.message = message;
		this.isRead = isRead;
		this.date = date;
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

	public ObjectId getOwnerRef() {
		return ownerRef;
	}

	public void setOwnerRef(ObjectId ownerRef) {
		this.ownerRef = ownerRef;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Notification that = (Notification) o;
		return isRead == that.isRead && Objects.equals(id, that.id) && Objects.equals(message, that.message) && Objects.equals(date, that.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, message, isRead, date);
	}

	@Override
	public int compareTo(Notification o) {
		return date.compareTo(o.date);
	}
}
