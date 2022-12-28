package persistence.entity.advertising;

import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Advertising implements Entity<Advertising> {

	private ObjectId id;
	private String name;
	private String description;
	private String url;

	@Nullable
	private String link;

	private Date startDate;
	private Date endDate;
	private int nbViews;
	private double price;
	private boolean isActive;
	private boolean isPayed;

	private ObjectId company;
	public Advertising() { }

	public Advertising(String name, String description, String url, @Nullable String link, Date startDate, Date endDate, double price, ObjectId company) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.link = link;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.company = company;
	}

	public void handleOnCreate(){
		this.id = null;
		this.nbViews = 0;
		setActive(false);
		setPayed(false);
		this.price = (this.getEndDate().getTime() - this.getStartDate().getTime())/(8640000);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Nullable
	public String getLink() {
		return link;
	}

	public void setLink(@Nullable String link) {
		this.link = link;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNbViews() {
		return nbViews;
	}

	public void setNbViews(int nbViews) {
		this.nbViews = nbViews;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean payed) {
		isPayed = payed;
	}

	public ObjectId getCompany() {
		return company;
	}

	public void setCompany(ObjectId company) {
		this.company = company;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Advertising that = (Advertising) o;
		return nbViews == that.nbViews && Double.compare(that.price, price) == 0 && isActive == that.isActive && isPayed == that.isPayed && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(url, that.url) && Objects.equals(link, that.link) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(company, that.company);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, url, link, startDate, endDate, nbViews, price, isActive, isPayed, company);
	}

	@Override
	public int compareTo(Advertising o) {
		return name.compareTo(o.name);
	}
}
