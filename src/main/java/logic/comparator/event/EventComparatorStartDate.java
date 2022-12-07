package logic.comparator.event;

import logic.comparator.AbstractComparator;
import logic.comparator.Order;
import persistence.entity.event.Event;

/**
 * This class is used to compare two events by their startDate.
 */
public class EventComparatorStartDate extends AbstractComparator<Event> {

	public EventComparatorStartDate() {
		super();
	}

	public EventComparatorStartDate(Order order) {
		super(order);
	}

	/**
	 * Compare two events by their startDate and the order.
	 *
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return 0 if the arguments are identical, -1 if the first argument is less than the second, 1 if the first argument is greater than the second.
	 */
	@Override
	public int compare(Event o1, Event o2) {
		return getOrderValue() * o1.getStartDate().compareTo(o2.getStartDate());
	}
}
