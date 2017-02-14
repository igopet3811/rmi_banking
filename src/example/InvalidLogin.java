package example;

public class InvalidLogin extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLogin(String message) {
		super(message);
	}
}
