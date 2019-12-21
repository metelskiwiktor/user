package pl.wiktor.management.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.exception.AccountAlreadyRegisteredException;
import pl.wiktor.management.user.exception.AccountInvalidTokenException;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.mapper.Mapper;
import pl.wiktor.management.user.repository.AccountRepositoryJpa;
import pl.wiktor.management.user.repository.ActiveUserRepositoryJpa;
import pl.wiktor.management.user.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepositoryJpa accountRepository;
    private ActiveUserRepositoryJpa activeUserRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccountServiceImpl(AccountRepositoryJpa accountRepository, ActiveUserRepositoryJpa activeUserRepository) {
        this.accountRepository = accountRepository;
        this.activeUserRepository = activeUserRepository;
    }

    public void register(AccountDTO accountDTO) {
        Account account = Mapper.map(accountDTO);
        accountRepository.existsAccountByLogin(account.getLogin()).filter($ -> !$).orElseThrow(AccountAlreadyRegisteredException::new);
        accountRepository.save(account);
        logger.info("Account successful registered");
    }


    public void changePassword(String token, String newPassword) {
        activeUserRepository.existsActiveAccountByToken(token).filter($ -> $).orElseThrow(AccountInvalidTokenException::new);
        String login = activeUserRepository.getActiveAccountByToken(token).getLogin();
        Account account = accountRepository.getAccountByLogin(login);
        account.setPassword(newPassword);
        accountRepository.save(account);
        logger.info("Password successful changed");
    }
}
