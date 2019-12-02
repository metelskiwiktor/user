package pl.wiktor.management.user.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.exception.AccountException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
//import java.util.logging.Logger;

@Repository
public class AccountRepository {
    private EntityManager entityManager;

    @Autowired
    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addAccount(Account account) throws AccountException {
        int accounts = entityManager.createQuery("SELECT c FROM Account c WHERE c.login LIKE :login ")
                .setParameter("login", account.getLogin())
                .getResultList()
                .size();

        if(accounts > 0){
            throw new AccountException("Account already registered");
        }

        entityManager.merge(account);
    }

    @Transactional
    public void deleteAll(){
        entityManager.clear();
    }
}
//    public Account getUserByToken(String token){
//        return activeUsers.entrySet()
//                .stream()
//                .filter(entry -> entry.getValue().getUuid().toString().equals(token))
//                .map(Map.Entry::getKey)
//                .findFirst()
//                .get();
//    }
//
//    public Map<Account, Token> getActiveUsers() {
//        System.out.println(activeUsers.keySet());
//        return activeUsers;
//    }
//
//    public void addUser(Account account, Token token) {
//        activeUsers.put(account, token);
//    }