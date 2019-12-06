package pl.wiktor.management.user.helper;

import org.springframework.stereotype.Component;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.entity.ActiveAccount;
import pl.wiktor.management.user.model.enums.TableSearcher;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class QueryHelper {
    private final EntityManager entityManager;

    public QueryHelper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isAccountInDb(TableSearcher tableSearcher, String fieldValue){
        int accounts = entityManager.createQuery(
                "SELECT c FROM " + tableSearcher.getTable() + " c WHERE c."+ tableSearcher.getField() + " LIKE :"+tableSearcher.getField()
        )
                .setParameter(tableSearcher.getField(), fieldValue)
                .getResultList()
                .size();

        return accounts > 0;
    }

    public Account getAccountByLogin(String login){
        return entityManager.createQuery("SELECT  c FROM Account c WHERE c.login LIKE :login", Account.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    public void deleteAccountFromActiveAccount(String token){
        entityManager.createQuery("DELETE FROM ActiveAccount c WHERE c.token LIKE :token")
                .setParameter("token", token)
                .executeUpdate();
    }

    public boolean isAccountExistWithPassword(String login, String password) {
        final List<Account> accounts = entityManager.createQuery("SELECT c FROM Account c WHERE c.login LIKE :login AND c.password LIKE :password", Account.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getResultList();
        return accounts.size() > 0;
    }

    public Account getAccountByToken(String token){
        String login = entityManager.createQuery("SELECT c FROM ActiveAccount c WHERE c.token LIKE :token", ActiveAccount.class)
                .setParameter("token", token)
                .getSingleResult()
                .getLogin();

        return getAccountByLogin(login);
    }
}
