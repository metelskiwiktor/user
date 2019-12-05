package pl.wiktor.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.response.AccountPasswordDTO;
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
    private final static String logout = account + "logout/";
    private final static String password = account + "password/";

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
    public String login(@RequestBody Account account){
        return activeUserService.login(account);
    }

    @GetMapping(value = logout)
    public void logout(@RequestHeader(value = "Token") String token){
        activeUserService.logout(token);
    }

    @PostMapping(value = password, consumes = "application/json")
    public void changePassword(@RequestHeader(value = "Token") String token, @RequestBody AccountPasswordDTO accountPasswordDTO){
        accountService.changePassword(token, accountPasswordDTO.getPassword());
    }
}
