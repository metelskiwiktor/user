package pl.wiktor.management.user.exception;

public class AccountInvalidPasswordException extends AccountException {
    public AccountInvalidPasswordException() {
        super("Invalid password");
    }
}
