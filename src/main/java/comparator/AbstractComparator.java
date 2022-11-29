package comparator;

import java.util.Comparator;

/**
 * Abstract comparator.
 * This class is used to compare two objects.
 *
 * @param <T> The type of the objects to compare.
 */
public abstract class AbstractComparator<T> implements Comparator<T> {

	/**
	 * Order used to compare the objects.
	 */
	private Order order;

	public AbstractComparator() {
		this(Order.ASC);
	}

	public AbstractComparator(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public int getOrderValue() {
		return order.getValue();
	}

	public void setAscOrder() {
		this.order = Order.ASC;
	}

	public void setDescOrder() {
		this.order = Order.DESC;
	}
}
