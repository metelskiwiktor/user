package pl.wiktor.management.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktor.management.user.model.entity.ActiveAccount;

import java.util.Optional;

public interface ActiveUserRepositoryJpa extends JpaRepository<ActiveAccount, Integer> {
    ActiveAccount getActiveAccountByToken(String token);
    Optional<Boolean> existsActiveAccountByToken(String token);
    Optional<Boolean>  existsActiveAccountByLogin(String login);
    void deleteActiveAccountByToken(String token);
}
