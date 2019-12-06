package pl.wiktor.management.user.repository;

import pl.wiktor.management.user.model.enums.TableSearcher;

public interface ActiveUserRepository {
    void login(String login, String token);
    void logout(String token);
    boolean isAccountLoggedIn(TableSearcher tableSearcher, String fieldValue);
}
