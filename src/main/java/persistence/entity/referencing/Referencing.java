package persistence.entity.referencing;

import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Referencing implements Entity<Referencing> {
	///TODO: Optionnelles ?
	private ObjectId id;
	public Referencing() { }
	private double price;
	private Date paymentDate;
	private Date startDate;
	private Date expirationDate;
	private String status;
	private int importanceLevel;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getImportanceLevel() {
		return importanceLevel;
	}

	public void setImportanceLevel(int importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Referencing that = (Referencing) o;
		return Double.compare(that.price, price) == 0 && importanceLevel == that.importanceLevel && Objects.equals(id, that.id) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(startDate, that.startDate) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(status, that.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, price, paymentDate, startDate, expirationDate, status, importanceLevel);
	}
	@Override
	public int compareTo(Referencing o) {
		return importanceLevel-o.getImportanceLevel();
	}
}
