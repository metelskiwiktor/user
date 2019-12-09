package pl.wiktor.management.user.exception;

public class AccountInvalidLoginException extends AbstractAccountException {
    public AccountInvalidLoginException() {
        super("Invalid login");
    }
}
