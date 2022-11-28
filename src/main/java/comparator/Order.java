package comparator;

public enum Order {
	ASC(1),
	DESC(-1);

	private final int value;

	Order(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
