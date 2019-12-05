package pl.wiktor.management.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.repository.ActiveUserRepository;

import java.util.UUID;

@Service
public class ActiveUserService {
    private ActiveUserRepository activeUserRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ActiveUserService(ActiveUserRepository activeUserRepository) {
        this.activeUserRepository = activeUserRepository;
    }


    public boolean login(String login, UUID token) {
        try {
            activeUserRepository.login(login, token);
            logger.info("Account successful logged in");
            return true;
        } catch (AccountException e) {
            logger.error("Account already logged in");
            return false;
        }
    }

    public void logout(String token){
        if(activeUserRepository.isAccountLoggedInByToken(token)){
            activeUserRepository.logout(token);
            logger.info("Account logged out");
        } else {
            logger.error("Account isn't logged in, can't be logged out");
        }
    }
}
