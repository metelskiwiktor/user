package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.ActiveAccount;
import pl.wiktor.management.user.entity.Token;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public class ActiveUserRepository {
    private EntityManager entityManager;

    @Autowired
    public ActiveUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void login(String login, UUID token){
        entityManager.merge(new ActiveAccount(login, token));
    }
}
