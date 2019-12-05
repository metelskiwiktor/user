package pl.wiktor.management.user.helper;

import pl.wiktor.management.user.entity.Account;

import javax.persistence.EntityManager;
import java.util.UUID;

public class QueryHelper {
    private EntityManager entityManager;

    public QueryHelper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isAccountInDb(String fromTable, String login){
        int accounts = entityManager.createQuery("SELECT c FROM " + fromTable + " c WHERE c.login LIKE :login ")
                .setParameter("login", login)
                .getResultList()
                .size();

        return accounts > 0;
    }

    public boolean isAccountInDbByToken(String fromTable, String token){
        int accounts = entityManager.createQuery("SELECT c FROM " + fromTable + " c WHERE c.token LIKE :token ")
                .setParameter("token", token)
                .getResultList()
                .size();

        return accounts > 0;
    }

    public Account getAccountByLogin(String login, String fromTable){
        return (Account) entityManager.createQuery("SELECT  c FROM " + fromTable + " c WHERE c.login LIKE :login")
                .setParameter("login", login)
                .getSingleResult();
    }

    public void deleteAccountFromAccountRepository(String login){
        entityManager.createQuery("DELETE FROM ActiveAccount c WHERE c.login LIKE :login")
                .setParameter("login", login);
    }

    public void deleteAccountFromActiveAccount(String token){
        entityManager.createQuery("DELETE FROM ActiveAccount c WHERE c.token LIKE :token")
                .setParameter("token", token);
    }
}
