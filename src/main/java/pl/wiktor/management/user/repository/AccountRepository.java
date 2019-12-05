package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.Account;
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
        boolean isAlreadyRegisterAccount = queryHelper.isAccountInDb(Account.class.getSimpleName(), account.getLogin());

        if(isAlreadyRegisterAccount){
            throw new AccountException("Account already registered");
        }

        entityManager.merge(account);
    }
}