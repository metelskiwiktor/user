package pl.wiktor.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.repository.ActiveUserRepository;
import pl.wiktor.management.user.service.AccountService;
import pl.wiktor.management.user.service.ActiveUserService;

import java.util.UUID;

@RestController()
@RequestMapping("/")
public class AccountController {
    private AccountService accountService;
    private ActiveUserService activeUserService;

    private final static String login = "login/";
    private final static String register = "register/";
    private final static String account = "account/";

    @Autowired
    public AccountController(AccountService accountService, ActiveUserService activeUserService) {
        this.accountService = accountService;
        this.activeUserService = activeUserService;
    }

    @PostMapping(value = register, consumes = "application/json")
    public boolean register(@RequestBody Account account) {
        return accountService.register(account);
    }

    @PostMapping(value = login, consumes = "application/json")
    public UUID login(@RequestBody Account account){
        UUID token = UUID.randomUUID();
        // TODO: 05.12.2019 It should be in service
        if(activeUserService.login(account.getLogin(), token)){
            return token;
        } else {
            return null;
        }
    }

    @GetMapping(value = account+"/logout")
    public void logout(@RequestHeader(value = "Token") String token){
        activeUserService.logout(token);
    }
}
