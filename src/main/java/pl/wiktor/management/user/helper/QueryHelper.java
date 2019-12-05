package pl.wiktor.management.user.helper;

import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.enums.TableSearcher;

import javax.persistence.EntityManager;
import java.util.UUID;

public class QueryHelper {
    private EntityManager entityManager;

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

    public Account getAccountByLogin(String login, String fromTable){
        return entityManager.createQuery("SELECT  c FROM " + fromTable + " c WHERE c.login LIKE :login", Account.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    public void deleteAccountFromActiveAccount(String token){
        entityManager.createQuery("DELETE FROM ActiveAccount c WHERE c.token LIKE :token")
                .setParameter("token", "d292fe00-0ed2-490c-aa96-1fb6f99ff753")
                .executeUpdate();
    }
}
