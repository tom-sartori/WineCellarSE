package comparator.partner;

import comparator.Comparator;
import comparator.Order;
import entity.partner.Partner;

public class PartnerComparatorLink extends Comparator<Partner> {

	public PartnerComparatorLink() {
		super();
	}

	public PartnerComparatorLink(Order order) {
		super(order);
	}

	@Override
	public int compare(Partner o1, Partner o2) {
		return getOrderValue() * o1.getLink().compareTo(o2.getLink());
	}
}