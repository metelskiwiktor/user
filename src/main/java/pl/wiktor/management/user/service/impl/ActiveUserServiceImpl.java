package pl.wiktor.management.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.exception.AccountInvalidLoginException;
import pl.wiktor.management.user.exception.AccountInvalidPasswordException;
import pl.wiktor.management.user.exception.AccountInvalidTokenException;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.entity.ActiveAccount;
import pl.wiktor.management.user.model.mapper.Mapper;
import pl.wiktor.management.user.repository.AccountRepositoryJpa;
import pl.wiktor.management.user.repository.ActiveUserRepositoryJpa;
import pl.wiktor.management.user.service.ActiveUserService;

import javax.transaction.Transactional;
import java.util.UUID;


@Transactional
@Service
public class ActiveUserServiceImpl implements ActiveUserService {
    private ActiveUserRepositoryJpa activeUserRepository;
    private AccountRepositoryJpa accountRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ActiveUserServiceImpl(ActiveUserRepositoryJpa activeUserRepository, AccountRepositoryJpa accountRepository) {
        this.activeUserRepository = activeUserRepository;
        this.accountRepository = accountRepository;
    }

    public String login(AccountDTO accountDTO) {
        Account account = Mapper.map(accountDTO);
        String token = UUID.randomUUID().toString();
        accountRepository.existsAccountByLogin(account.getLogin()).filter($ -> $).orElseThrow(AccountInvalidLoginException::new);
        accountRepository.existsAccountByLoginAndPassword(account.getLogin(), account.getPassword()).filter($ -> $).orElseThrow(AccountInvalidPasswordException::new);
        activeUserRepository.save(new ActiveAccount(account.getLogin(), token));
        logger.info("Account logged in");
        return token;
    }

    public void logout(String token){
        activeUserRepository.existsActiveAccountByToken(token).filter($ -> $).orElseThrow(AccountInvalidTokenException::new);
        activeUserRepository.deleteActiveAccountByToken(token);
        logger.info("Account logged out");
    }


}
