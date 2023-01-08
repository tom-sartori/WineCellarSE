package persistence.entity.company;

import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import persistence.entity.Entity;
import persistence.entity.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company implements Entity<Company> {

	private ObjectId id;
	private String name;
	private String type;
	private String address;
	private boolean isAccessible;
	private ObjectId masterManager;
	private List<ObjectId> managerList;
	private ObjectId cellar;
	private ArrayList<ObjectId> followerList;
	@Nullable
	private String phoneNumber;
	@Nullable
	private String description;
	@Nullable
	private String websiteLink;
	@Nullable
	private String logoLink;

	public Company() { }

	public Company(String name, String type, String address, boolean isAccessible, ObjectId masterManager, List<ObjectId> managerList, ObjectId cellar, @Nullable String phoneNumber, @Nullable String description, @Nullable String websiteLink, @Nullable String logoLink) {
		this.name = name;
		this.type = type;
		this.address = address;
		this.isAccessible = isAccessible;
		this.masterManager = masterManager;
		this.managerList = managerList;
		this.cellar = cellar;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.websiteLink = websiteLink;
		this.logoLink = logoLink;
		this.followerList = new ArrayList<>();
	}

	@Override
	public void handleOnCreate() {
		setId(null);
		setAccessible(false);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAccessible() {
		return isAccessible;
	}

	public void setAccessible(boolean accessible) {
		isAccessible = accessible;
	}

	public ObjectId getMasterManager() {
		return masterManager;
	}

	public void setMasterManager(ObjectId masterManager) {
		this.masterManager = masterManager;
	}

	public List<ObjectId> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<ObjectId> managerList) {
		this.managerList = managerList;
	}

	public ArrayList<ObjectId> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(ArrayList<ObjectId> followerList) {
		this.followerList = followerList;
	}

	public ObjectId getCellar() {
		return cellar;
	}

	public void setCellar(ObjectId cellar) {
		this.cellar = cellar;
	}

	@Nullable
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(@Nullable String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(@Nullable String description) {
		this.description = description;
	}

	@Nullable
	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(@Nullable String websiteLink) {
		this.websiteLink = websiteLink;
	}

	@Nullable
	public String getLogoLink() {
		return logoLink;
	}

	public void setLogoLink(@Nullable String logoLink) {
		this.logoLink = logoLink;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Company company = (Company) o;
		return isAccessible == company.isAccessible && Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(type, company.type) && Objects.equals(address, company.address) && Objects.equals(masterManager, company.masterManager) && Objects.equals(managerList, company.managerList) && Objects.equals(cellar, company.cellar) && Objects.equals(followerList, company.followerList) && Objects.equals(phoneNumber, company.phoneNumber) && Objects.equals(description, company.description) && Objects.equals(websiteLink, company.websiteLink) && Objects.equals(logoLink, company.logoLink);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type, address, isAccessible, masterManager, managerList, cellar, followerList, phoneNumber, description, websiteLink, logoLink);
	}

	@Override
	public int compareTo(Company o) {
		return name.compareTo(o.name);
	}
}
