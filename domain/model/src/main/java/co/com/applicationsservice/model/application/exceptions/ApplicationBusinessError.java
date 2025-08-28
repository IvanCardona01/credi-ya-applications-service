package co.com.applicationsservice.model.application.exceptions;

public class ApplicationBusinessError extends RuntimeException {
    public ApplicationBusinessError(String message) {
        super(message);
    }
}
