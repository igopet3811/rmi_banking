package example;

public class InvalidLogin extends Exception {

	public InvalidLogin(String message) {
		super("ERROR LOGGING IN: " + message);
	}
}
