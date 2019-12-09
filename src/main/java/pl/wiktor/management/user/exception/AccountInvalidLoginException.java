package pl.wiktor.management.user.exception;

public class AccountInvalidLoginException extends AccountException {
    public AccountInvalidLoginException() {
        super("Invalid login");
    }
}
