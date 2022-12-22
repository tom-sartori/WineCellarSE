package persistence.entity.advertising;

import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.util.Date;
import java.util.Objects;


public class Advertising implements Entity<Advertising> {

	public ObjectId id;
	public String name;
	public String description;
	public String url;

	@Nullable
	public String link;

	public Date startDate;
	public Date endDate;
	public int nbViews;
	public double price;
	public boolean isActive;
	public boolean isPayed;

	public Advertising() { }

	public Advertising(String name, String description, String url, @Nullable String link, Date startDate, Date endDate, double price) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.link = link;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public void handleOnCreate(){
		this.id = null;
		this.nbViews = 0;
		this.isActive = false;
		this.isPayed = false;
		price = (endDate.getTime() - startDate.getTime())*100;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Advertising that = (Advertising) o;
		return nbViews == that.nbViews && Double.compare(that.price, price) == 0 && isActive == that.isActive && isPayed == that.isPayed && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(url, that.url) && Objects.equals(link, that.link) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, url, link, startDate, endDate, nbViews, price, isActive, isPayed);
	}

	@Override
	public int compareTo(Advertising o) {
		return name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return "Advertising{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", url=" + url +
				", link='" + link + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", nbViews=" + nbViews +
				", price=" + price +
				", isActive=" + isActive +
				", isPayed=" + isPayed +
				'}';
	}
}
