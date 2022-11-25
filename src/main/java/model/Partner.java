package model;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Objects;

public class Partner implements Comparable<Partner> {

	private ObjectId id;
	private String name;
	private String link;
	private String matIcon;

	public Partner(String name, String link, String matIcon) {
		this.name = name;
		this.link = link;
		this.matIcon = matIcon;
	}

	public Partner() {
	}

	public ObjectId get_id() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public String getMatIcon() {
		return matIcon;
	}

	public Document toDocument() {
		return new Document("name", name)
				.append("link", link)
				.append("matIcon", matIcon);
	}

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
