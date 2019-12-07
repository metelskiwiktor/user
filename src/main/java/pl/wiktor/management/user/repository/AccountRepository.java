package pl.wiktor.management.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktor.management.user.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsAccountByLogin(String login);
    Account getAccountByLogin(String login);
    boolean existsAccountByLoginAndPassword(String login, String password);
}
