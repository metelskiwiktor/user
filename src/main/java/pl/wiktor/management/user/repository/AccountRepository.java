package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.enums.TableSearcher;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.helper.QueryHelper;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class AccountRepository {
    private EntityManager entityManager;
    private QueryHelper queryHelper;

    @Autowired
    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryHelper = new QueryHelper(entityManager);
    }

    @Transactional
    public void addAccount(Account account) throws AccountException {
        boolean isAlreadyRegisterAccount = queryHelper.isAccountInDb(TableSearcher.AccountByLogin, account.getLogin());

        if(isAlreadyRegisterAccount){
            throw new AccountException("Account already registered");
        }

        entityManager.merge(account);
    }

    public boolean isUserExist(String login){
        return queryHelper.isAccountInDb(TableSearcher.AccountByLogin, login);
    }

    public boolean isAccountExistWithPassword(String login, String password){
        return queryHelper.isAccountExistWithPassword(login, password);
    }
}