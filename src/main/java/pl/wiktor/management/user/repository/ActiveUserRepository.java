package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.ActiveAccount;
import pl.wiktor.management.user.entity.enums.TableSearcher;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.helper.QueryHelper;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public class ActiveUserRepository {
    private EntityManager entityManager;
    private QueryHelper queryHelper;

    @Autowired
    public ActiveUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryHelper = new QueryHelper(entityManager);
    }

    @Transactional
    public void login(String login, UUID token) throws AccountException {
        boolean isUserLoggedIn = queryHelper.isAccountInDb(TableSearcher.ActiveAccountByLogin, login);
        if(isUserLoggedIn){
            throw new AccountException("User already logged in");
        }
        entityManager.merge(new ActiveAccount(login, token.toString()));
    }

    @Transactional
    public void logout(String token){
        queryHelper.deleteAccountFromActiveAccount(token);
        entityManager.flush();
    }

    public Account getAccountByLogin(String login){
        return queryHelper.getAccountByLogin(login, Account.class.getSimpleName());
    }

    public boolean isAccountLoggedIn(TableSearcher tableSearcher, String token){
        return queryHelper.isAccountInDb(tableSearcher, token);
    }

    public boolean isAccountExist(Account account) {
        return queryHelper.getAccountByLogin(account.getLogin(), "Account") == account;
    }
}
