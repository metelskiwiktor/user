package pl.wiktor.management.user.exception;

public class AccountPasswordException extends RuntimeException {
    public AccountPasswordException(String message) {
        super(message);
    }
}
