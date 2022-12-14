package persistence.entity.bottle;

import com.mongodb.lang.Nullable;
import persistence.entity.Entity;

import java.util.ArrayList;
import java.util.Objects;

public class Bottle implements Entity<Bottle> {

    private String bottleName;
    @Nullable
    private int vintage;
    private String appellation;
    private String bottleImage;
    private double price;
    private String producer;
    private double alcoholPercentage;
    private double bottleSize;
    private String sizeUnit;
    private String category;
    private ArrayList<String> grapeList;

    public Bottle() {
    }

    public Bottle(String bottleName, int vintage, String appellation, String bottleImage, double price, String producer, double alcoholPercentage, double bottleSize, String sizeUnit, String category, ArrayList<String> grapeList) {
        this.bottleName = bottleName;
        this.vintage = vintage;
        this.appellation = appellation;
        this.bottleImage = bottleImage;
        this.price = price;
        this.producer = producer;
        this.alcoholPercentage = alcoholPercentage;
        this.bottleSize = bottleSize;
        this.sizeUnit = sizeUnit;
        this.category = category;
        this.grapeList = grapeList;
    }

    public String getBottleName() {
        return bottleName;
    }

    public void setBottleName(String bottleName) {
        this.bottleName = bottleName;
    }

    public int getVintage() {
        return vintage;
    }

    public void setVintage(int vintage) {
        this.vintage = vintage;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getBottleImage() {
        return bottleImage;
    }

    public void setBottleImage(String bottleImage) {
        this.bottleImage = bottleImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public double getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(double bottleSize) {
        this.bottleSize = bottleSize;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getGrapeList() {
        return grapeList;
    }

    public void setGrapeList(ArrayList<String> grapeList) {
        this.grapeList = grapeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bottle bottle = (Bottle) o;
        return vintage == bottle.vintage && Double.compare(bottle.price, price) == 0 && Double.compare(bottle.alcoholPercentage, alcoholPercentage) == 0 && Double.compare(bottle.bottleSize, bottleSize) == 0 && Objects.equals(bottleName, bottle.bottleName) && Objects.equals(appellation, bottle.appellation) && Objects.equals(bottleImage, bottle.bottleImage) && Objects.equals(producer, bottle.producer) && Objects.equals(sizeUnit, bottle.sizeUnit) && Objects.equals(category, bottle.category) && Objects.equals(grapeList, bottle.grapeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottleName, vintage, appellation, bottleImage, price, producer, alcoholPercentage, bottleSize, sizeUnit, category, grapeList);
    }

    @Override
    public int compareTo(Bottle o) {
        return bottleName.compareTo(o.getBottleName());
    }

}
