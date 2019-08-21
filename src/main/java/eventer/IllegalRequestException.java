package eventer;

public class IllegalRequestException extends Exception {

	public IllegalRequestException(String message) {
		super(message);
	}

	public IllegalRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
