package ramos.maxuel.socialmedia.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private final List<Error> errors = new ArrayList<>();

	public BusinessException(String message) {
		super(message);
		this.addError(new Error(message));
	}

	public BusinessException(Error error) {
		super(error.getMessage());
		this.addError(error);
	}

    public BusinessException(List<Error> errors) {
        super("Validation Errors");
        this.addErrors(errors);
    }

	protected void addErrors(List<Error> errors) {
		this.errors.addAll(errors);
	}

	protected void addError(Error error) {
		this.errors.add(error);
	}

    public List<Error> getErrors() {
        return errors;
    }
}
