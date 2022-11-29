package logic.comparator.partner;

import logic.comparator.AbstractComparator;
import logic.comparator.Order;
import persistence.entity.partner.Partner;

/**
 * This class is used to compare two partners by their links.
 */
public class PartnerComparatorLink extends AbstractComparator<Partner> {

	public PartnerComparatorLink() {
		super();
	}

	public PartnerComparatorLink(Order order) {
		super(order);
	}

	/**
	 * Compare two partners by their links and the order.
	 *
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return 0 if the arguments are identical, -1 if the first argument is less than the second, 1 if the first argument is greater than the second.
	 */
	@Override
	public int compare(Partner o1, Partner o2) {
		return getOrderValue() * o1.getLink().compareTo(o2.getLink());
	}
}
