package persistence.entity.conversation;

import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;

public class Message implements Entity<Message> {

	private String sender;
	private String message;
	private Date date;

	public Message() {
	}

	public Message(String sender, String message) {
		this.sender = sender;
		this.message = message;
		this.date = new Date();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
		Message message1 = (Message) o;
		return Objects.equals(sender, message1.sender) && Objects.equals(message, message1.message) && Objects.equals(date, message1.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sender, message, date);
	}

	@Override
	public int compareTo(Message o) {
		return date.compareTo(o.date);
	}
}
