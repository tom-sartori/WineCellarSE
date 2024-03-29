package persistence.entity.referencing;

import facade.Facade;
import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Referencing implements Entity<Referencing> {
	private ObjectId id;
	private double price;
	private Date paymentDate;
	private Date startDate;
	private Date expirationDate;
	private String status;
	private int importanceLevel;
	private ObjectId company;
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
	public ObjectId getCompany() {
		return company;
	}
	public void setCompany(ObjectId company) {
		this.company = company;
	}
	public int getImportanceLevel() {
		return importanceLevel;
	}
	public void setImportanceLevel(int importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	public Referencing() { }
	public Referencing(Date paymentDate, Date startDate, Date expirationDate, int importanceLevel, ObjectId company) {
		this.paymentDate = paymentDate;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
		this.importanceLevel = importanceLevel;
		this.company = company;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Referencing that = (Referencing) o;
		return Double.compare(that.price, price) == 0 && importanceLevel == that.importanceLevel && Objects.equals(id, that.id) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(startDate, that.startDate) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(status, that.status) && Objects.equals(company, that.company);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, price, paymentDate, startDate, expirationDate, status, importanceLevel, company);
	}
	@Override
	public int compareTo(Referencing o) {
		return importanceLevel-o.getImportanceLevel();
	}
	@Override
	public void handleOnCreate() {
		this.id = null;
		this.price = Facade.getInstance().calculatePriceReferencing(getStartDate(),getExpirationDate(),getImportanceLevel());
	}
}
