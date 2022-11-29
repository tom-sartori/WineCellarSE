package comparator;

import java.util.Comparator;

public abstract class AbstractComparator<T> implements Comparator<T> {

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
