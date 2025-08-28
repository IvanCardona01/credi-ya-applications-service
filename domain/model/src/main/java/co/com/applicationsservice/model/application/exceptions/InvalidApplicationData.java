package co.com.applicationsservice.model.application.exceptions;

public class InvalidApplicationData extends RuntimeException {
    public InvalidApplicationData(String message) {
        super(message);
    }
}
