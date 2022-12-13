package persistence.entity.advertizing;

import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import persistence.entity.Entity;

import java.awt.*;
import java.util.Date;
import java.util.Objects;


public class Advertizing implements Entity<Advertizing> {

	/// TODO : Vérifier quels attributs sont optionnels.
	/// TODO : isActive faux à la création, nbViews = 0
	public ObjectId id;
	public String name;
	public String description;
	public Image image;

	@Nullable
	public String link;

	public Date startDate;
	public Date endDate;
	public int nbViews;
	public double price;
	public boolean isActive;
	public boolean isPayed;

	public Advertizing() { }

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
		Advertizing that = (Advertizing) o;
		return nbViews == that.nbViews && Double.compare(that.price, price) == 0 && isActive == that.isActive && isPayed == that.isPayed && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(image, that.image) && Objects.equals(link, that.link) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, image, link, startDate, endDate, nbViews, price, isActive, isPayed);
	}

	/// TODO : Update compareTo method if needed.
	@Override
	public int compareTo(Advertizing o) {
		return name.compareTo(o.name);
	}
}
