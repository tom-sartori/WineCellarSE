package persistence.entity.cellar;

import org.bson.types.ObjectId;
import persistence.entity.Entity;
import persistence.entity.bottle.Bottle;

import java.util.Objects;

public class BottleQuantity implements Entity<BottleQuantity> {

    private Bottle bottle;
    private int quantity;

    public BottleQuantity() {
    }

    public BottleQuantity(Bottle bottle, int quantity) {
        this.bottle = bottle;
        this.quantity = quantity;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BottleQuantity that = (BottleQuantity) o;
        return quantity == that.quantity && Objects.equals(bottle, that.bottle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottle, quantity);
    }

    @Override
    public int compareTo(BottleQuantity o) {
        return bottle.compareTo(o.bottle);
    }
}
