package pl.wiktor.management.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.model.dto.response.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.enums.TableSearcher;
import pl.wiktor.management.user.model.mapper.Mapper;
import pl.wiktor.management.user.repository.AccountRepositoryImpl;
import pl.wiktor.management.user.repository.ActiveUserRepository;

@Service
public class AccountService {
    private AccountRepositoryImpl accountRepository;
    private ActiveUserRepository activeUserRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccountService(AccountRepositoryImpl accountRepository, ActiveUserRepository activeUserRepository) {
        this.accountRepository = accountRepository;
        this.activeUserRepository = activeUserRepository;
    }

    public boolean register(AccountDTO accountDTO){
        Account account = Mapper.map(accountDTO);
        try{
            accountRepository.addAccount(account);
            logger.info("Account successful registered");
            return true;
        } catch (AccountException e) {
            logger.error("Account already registered");
            return false;
        }
    }

    public void changePassword(String token, String newPassword) {
        if(activeUserRepository.isAccountLoggedIn(TableSearcher.ActiveAccountByToken, token)){
            accountRepository.changePassword(token, newPassword);
            logger.info("Password successful changed");
        } else {
            logger.error("Account must be logged in to change password");
        }
    }
}
