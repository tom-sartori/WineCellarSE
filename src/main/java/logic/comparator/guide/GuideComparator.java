package logic.comparator.guide;

import logic.comparator.AbstractComparator;
import logic.comparator.Order;
import persistence.entity.guide.Guide;

/**
 * This class is used to compare two guides by their titles.
 */
public class GuideComparator extends AbstractComparator<Guide> {

    public GuideComparator() {
        super();
    }

    public GuideComparator(Order order) {
        super(order);
    }

    /**
     * Compare two guides by their titles and the order.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return 0 if the arguments are identical, -1 if the first argument is less than the second, 1 if the first argument is greater than the second.
     */
    @Override
    public int compare(Guide o1, Guide o2) {
        return getOrderValue() * o1.getTitle().compareTo(o2.getTitle());
    }
}
