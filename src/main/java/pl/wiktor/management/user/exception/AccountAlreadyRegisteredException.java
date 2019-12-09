package pl.wiktor.management.user.exception;

public class AccountAlreadyRegisteredException extends AccountException {

    public AccountAlreadyRegisteredException() {
        super("Account already registered");
    }
}
