package pl.wiktor.management.user.repository;

import org.springframework.stereotype.Repository;
import pl.wiktor.management.user.entity.Account;

import java.util.List;

@Repository
public class UserRepository {
    private List<Account> accounts;

    public UserRepository(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addUser(Account account){
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void changePassword(Account account, String newPassword){
        accounts.stream()
                .filter(u -> u.getLogin().equals(account.getLogin()))
                .findFirst()
                .get()
                .setPassword(newPassword);
    }

    public boolean containsUserAuth(Account account){
        return accounts.stream().anyMatch(u -> u.getLogin().equals(account.getLogin()) && u.getPassword().equals(account.getPassword()));
    }

    public Account getUserByName(String name){
        return accounts.stream()
                .filter(u -> u.getLogin().equals(name))
                .findFirst()
                .get();
    }
}
