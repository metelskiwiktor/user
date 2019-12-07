package pl.wiktor.management.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wiktor.management.user.model.entity.ActiveAccount;

public interface ActiveUserRepository extends JpaRepository<ActiveAccount, Integer> {
    ActiveAccount getActiveAccountByToken(String token);
    boolean existsActiveAccountByToken(String token);
    void deleteActiveAccountByToken(String token);
    boolean existsActiveAccountByLogin(String login);
}
