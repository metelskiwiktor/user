package pl.wiktor.management.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.exception.AccountPasswordException;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.entity.ActiveAccount;
import pl.wiktor.management.user.model.mapper.Mapper;
import pl.wiktor.management.user.repository.AccountRepository;
import pl.wiktor.management.user.repository.ActiveUserRepository;
import pl.wiktor.management.user.service.ActiveUserService;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ActiveUserServiceImpl implements ActiveUserService {
    private ActiveUserRepository activeUserRepository;
    private AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ActiveUserServiceImpl(ActiveUserRepository activeUserRepository, AccountRepository accountRepository) {
        this.activeUserRepository = activeUserRepository;
        this.accountRepository = accountRepository;
    }

    public String login(AccountDTO accountDTO) {
        Account account = Mapper.map(accountDTO);
        String token = UUID.randomUUID().toString();
        try {
            loginIsInDb(account.getLogin());
            loginAndPasswordCorrect(account);
            activeUserRepository.save(new ActiveAccount(account.getLogin(), token));
            logger.info("Account successful logged in");
            return token;
        } catch (AccountPasswordException | AccountLoginException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Transactional
    public void logout(String token){
        if(activeUserRepository.existsActiveAccountByToken(token)){
            activeUserRepository.deleteActiveAccountByToken(token);
            logger.info("Account logged out");
        } else {
            logger.error("Account isn't logged in, can't be logged out");
        }
    }

    private void loginIsInDb(String login) throws AccountLoginException {
        if( !accountRepository.existsAccountByLogin(login)){
            throw new AccountLoginException("Account isn't registered");
        }
    }

    private void loginAndPasswordCorrect(Account account) throws AccountPasswordException{
        if( !accountRepository.existsAccountByLoginAndPassword(account.getLogin(), account.getPassword())){
            throw new AccountPasswordException("Password doesn't match");
        }
    }
}
