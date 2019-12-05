package pl.wiktor.management.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.enums.TableSearcher;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.exception.AccountPasswordException;
import pl.wiktor.management.user.repository.AccountRepository;
import pl.wiktor.management.user.repository.ActiveUserRepository;

import java.util.UUID;

@Service
public class ActiveUserService {
    private ActiveUserRepository activeUserRepository;
    private AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ActiveUserService(ActiveUserRepository activeUserRepository, AccountRepository accountRepository) {
        this.activeUserRepository = activeUserRepository;
        this.accountRepository = accountRepository;
    }

    private void loginIsInDb(String login) throws AccountLoginException {
        if( !accountRepository.isLoginInDb(login)){
            throw new AccountLoginException("Account isn't registered");
        }
    }

    private void loginAndPasswordCorrect(Account account) throws AccountPasswordException{
        if( !accountRepository.isAccountExistWithPassword(account.getLogin(), account.getPassword()) ){
            throw new AccountPasswordException("Password doesn't match");
        }
    }

    public boolean login(Account account, UUID token) {
        try {
            loginIsInDb(account.getLogin());
            loginAndPasswordCorrect(account);
            activeUserRepository.login(account.getLogin(), token);
            logger.info("Account successful logged in");
            return true;
        } catch (AccountException | AccountPasswordException | AccountLoginException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public void logout(String token){
        if(activeUserRepository.isAccountLoggedIn(TableSearcher.ActiveAccountByToken, token)){
            activeUserRepository.logout(token);
            logger.info("Account logged out");
        } else {
            logger.error("Account isn't logged in, can't be logged out");
        }
    }
}
