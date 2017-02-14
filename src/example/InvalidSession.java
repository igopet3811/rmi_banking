package example;

public class InvalidSession extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidSession(String message) {
		super(message);
	}
}
