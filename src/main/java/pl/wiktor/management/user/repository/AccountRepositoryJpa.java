package pl.wiktor.management.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktor.management.user.model.entity.Account;

import java.util.Optional;

public interface AccountRepositoryJpa extends JpaRepository<Account, Integer> {
    Optional<Boolean> existsAccountByLogin(String login);
    Account getAccountByLogin(String login);
    Optional<Boolean> existsAccountByLoginAndPassword(String login, String password);
}
