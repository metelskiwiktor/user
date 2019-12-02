package pl.wiktor.management.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean register(Account account){
        try{
            accountRepository.addAccount(account);
            logger.info("Account successful registered");
            return true;
        } catch (AccountException e) {
            logger.error("Account already registered");
            return false;
        }
    }
}
