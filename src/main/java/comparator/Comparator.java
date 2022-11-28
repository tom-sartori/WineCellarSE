package comparator;

public abstract class Comparator<T> implements java.util.Comparator<T> {

	private Order order;

	public Comparator() {
		this(Order.ASC);
	}

	public Comparator(Order order) {
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
