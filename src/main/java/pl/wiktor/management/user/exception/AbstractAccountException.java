package pl.wiktor.management.user.exception;

public abstract class AbstractAccountException extends RuntimeException{
    public AbstractAccountException(String s) {
        super(s);
    }
}
