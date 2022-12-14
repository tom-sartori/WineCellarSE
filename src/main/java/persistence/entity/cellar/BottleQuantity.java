package persistence.entity.cellar;

import org.bson.types.ObjectId;
import persistence.entity.Entity;
import persistence.entity.bottle.Bottle;

import java.util.Objects;

public class BottleQuantity implements Entity<BottleQuantity> {

    private ObjectId id;
    private Bottle bottle;
    private int quantity;

    public BottleQuantity() {
    }

    public BottleQuantity(Bottle bottle, int quantity) {
        this.bottle = bottle;
        this.quantity = quantity;
    }

    /**
    * Sets the id to null, to prevent the user from setting it
    */
    @Override
    public void handleOnCreate() {
        setId(null);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(bottle, that.bottle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bottle, quantity);
    }

    @Override
    public int compareTo(BottleQuantity o) {
        return bottle.compareTo(o.bottle);
    }
}
