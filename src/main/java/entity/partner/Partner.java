package entity.partner;

import entity.Entity;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

public class Partner implements Entity<Partner>, Serializable {

	private ObjectId id;
	private String name;
	private String link;
	private String matIcon;

	public Partner() { }

	public Partner(String name, String link, String matIcon) {
		this.name = name;
		this.link = link;
		this.matIcon = matIcon;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMatIcon() {
		return matIcon;
	}

	public void setMatIcon(String matIcon) {
		this.matIcon = matIcon;
	}

//	public Document toDocument() {
//		return new Document("name", name)
//				.append("link", link)
//				.append("matIcon", matIcon);
//	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Partner partner = (Partner) o;
		return id == partner.id && Objects.equals(name, partner.name) && Objects.equals(link, partner.link) && Objects.equals(matIcon, partner.matIcon);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, link, matIcon);
	}

	@Override
	public int compareTo(Partner o) {
		return name.compareTo(o.name);
	}
}
