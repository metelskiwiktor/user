package pl.wiktor.management.user.repository;

import pl.wiktor.management.user.model.entity.Account;

public interface AccountRepository {
    void addAccount(Account account);
    void changePassword(String token, String newPassword);
    boolean isAccountExistWithPassword(String login, String password);
    boolean isLoginInDb(String login);
}
