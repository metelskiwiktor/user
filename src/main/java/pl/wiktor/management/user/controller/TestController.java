package pl.wiktor.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.Token;
import pl.wiktor.management.user.exception.AccountException;
import pl.wiktor.management.user.repository.AccountRepository;
import pl.wiktor.management.user.service.AccountService;

import java.io.InvalidObjectException;

@RestController()
@RequestMapping("/test/")
public class TestController {
    private AccountService accountService;
    private final static String login = "login/";
    private final static String register = "register/";
    private final static String account = "account/";

    @Autowired
    public TestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = register, consumes = "application/json")
    public boolean register(@RequestBody Account account) {

        return accountService.register(account);
    }

//    @PostMapping(value = login, consumes = "application/json")
//    public Token login(@RequestBody Account account) throws InvalidObjectException {
//        if(userRepository.containsUserAuth(account)){
//            Token token = new Token();
//            account = userRepository.getUserByName(account.getLogin());
//            activeUserRepository.addUser(account, token);
//            return token;
//        }
//        throw new InvalidObjectException("No such element");
//    }





}
