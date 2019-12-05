package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.ActiveAccount;
import pl.wiktor.management.user.entity.enums.TableSearcher;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.helper.QueryHelper;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class ActiveUserRepository {
    private EntityManager entityManager;
    private QueryHelper queryHelper;

    @Autowired
    public ActiveUserRepository(EntityManager entityManager, QueryHelper queryHelper) {
        this.entityManager = entityManager;
        this.queryHelper = queryHelper;
    }

    @Transactional
    public void login(String login, String token) throws AccountLoginException {
        boolean isUserLoggedIn = queryHelper.isAccountInDb(TableSearcher.ActiveAccountByLogin, login);
        if(isUserLoggedIn){
            throw new AccountLoginException("User already logged in");
        }
        entityManager.merge(new ActiveAccount(login, token));
    }

    @Transactional
    public void logout(String token){
        queryHelper.deleteAccountFromActiveAccount(token);
        entityManager.flush();
    }

    public boolean isAccountLoggedIn(TableSearcher tableSearcher, String fieldValue){
        return queryHelper.isAccountInDb(tableSearcher, fieldValue);
    }

}
