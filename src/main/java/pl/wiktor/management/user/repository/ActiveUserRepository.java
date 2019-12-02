package pl.wiktor.management.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.Token;

import java.util.Map;

@Repository
public class ActiveUserRepository {
    private Map<Account, Token> activeUsers;

    public ActiveUserRepository(Map<Account, Token> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Account getUserByToken(String token){
        return activeUsers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getUuid().toString().equals(token))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
    }

    public Map<Account, Token> getActiveUsers() {
        System.out.println(activeUsers.keySet());
        return activeUsers;
    }

    public void addUser(Account account, Token token) {
        activeUsers.put(account, token);
    }
}
