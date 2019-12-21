package pl.wiktor.management.user.exception;

public class AccountInvalidPasswordException extends AbstractAccountException {
    public AccountInvalidPasswordException() {
        super("Invalid password");
    }
}
