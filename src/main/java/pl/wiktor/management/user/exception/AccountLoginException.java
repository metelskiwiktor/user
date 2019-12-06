package pl.wiktor.management.user.exception;

public class AccountLoginException extends AccountException {
    public AccountLoginException(String message) {
        super(message);
    }

    public AccountLoginException() {
        super("Invalid account details");
    }
}
