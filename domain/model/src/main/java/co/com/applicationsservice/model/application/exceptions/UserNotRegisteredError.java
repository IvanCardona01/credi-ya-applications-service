package co.com.applicationsservice.model.application.exceptions;

public class UserNotRegisteredError extends RuntimeException {
    public UserNotRegisteredError(String message) {
        super(message);
    }
}
