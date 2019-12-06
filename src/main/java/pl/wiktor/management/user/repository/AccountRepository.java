package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.enums.TableSearcher;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.helper.QueryHelper;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class AccountRepository {
    private EntityManager entityManager;
    private QueryHelper queryHelper;

    @Autowired
    public AccountRepository(EntityManager entityManager, QueryHelper queryHelper) {
        this.entityManager = entityManager;
        this.queryHelper = queryHelper;
    }

    @Transactional
    public void addAccount(Account account) throws AccountLoginException {
        boolean isAlreadyRegisterAccount = queryHelper.isAccountInDb(TableSearcher.AccountByLogin, account.getLogin());

        if(isAlreadyRegisterAccount){
            throw new AccountLoginException("Account already registered");
        }

        entityManager.merge(account);
    }

    @Transactional
    public void changePassword(String token, String newPassword){
        final Account account = queryHelper.getAccountByToken(token);
        account.setPassword(newPassword);
        entityManager.merge(account);
    }

    public boolean isAccountExistWithPassword(String login, String password){
        return queryHelper.isAccountExistWithPassword(login, password);
    }

    public boolean isLoginInDb(String login){
        return queryHelper.isAccountInDb(TableSearcher.AccountByLogin, login);
    }

}