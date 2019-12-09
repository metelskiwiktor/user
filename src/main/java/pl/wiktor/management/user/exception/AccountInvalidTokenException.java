package pl.wiktor.management.user.exception;

public class AccountInvalidTokenException extends AccountException{
    public AccountInvalidTokenException() {
        super("Invalid token");
    }
}
