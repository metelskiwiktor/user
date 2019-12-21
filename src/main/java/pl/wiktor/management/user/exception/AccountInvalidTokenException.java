package pl.wiktor.management.user.exception;

public class AccountInvalidTokenException extends AbstractAccountException {
    public AccountInvalidTokenException() {
        super("Invalid token");
    }
}
