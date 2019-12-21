package pl.wiktor.management.user.exception;

public class AccountAlreadyRegisteredException extends AbstractAccountException {

    public AccountAlreadyRegisteredException() {
        super("Account already registered");
    }
}
