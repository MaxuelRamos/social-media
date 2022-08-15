package ramos.maxuel.socialmedia.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final String MESSAGE_KEY = "Resource not found";

	private final Error error;

	public ResourceNotFoundException() {
		super(MESSAGE_KEY);
		error = new Error(MESSAGE_KEY);
	}

	public ResourceNotFoundException(String messageKey) {
		super(messageKey);
		error = new Error(messageKey);
	}

	public Error getError() {
		return error;
	}
}
