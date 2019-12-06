package pl.wiktor.management.user.exception;

public abstract class AccountException extends RuntimeException{
    public AccountException(String s) {
        super(s);
    }
}
