import facade.Facade;

public class App {
	public static void main(String[] args) {
		Facade facade = Facade.getInstance();
		System.out.println(facade.getPartners());
	}
}
