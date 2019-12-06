package pl.wiktor.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktor.management.user.model.dto.request.AccountPasswordDTO;
import pl.wiktor.management.user.model.dto.response.AccountDTO;
import pl.wiktor.management.user.service.AccountService;
import pl.wiktor.management.user.service.ActiveUserService;

@RestController()
@RequestMapping("/")
public class AccountController {
    private AccountService accountService;
    private ActiveUserService activeUserService;

    private final static String login = "login/";
    private final static String register = "register/";
    private final static String account = "account/";
    private final static String logout = account + "logout/";
    private final static String changePassword = account + "password/";

    @Autowired
    public AccountController(AccountService accountService, ActiveUserService activeUserService) {
        this.accountService = accountService;
        this.activeUserService = activeUserService;
    }

    @PostMapping(value = register, consumes = "application/json")
    public boolean register(@RequestBody AccountDTO accountDTO) {
        return accountService.register(accountDTO);
    }

    @PostMapping(value = login, consumes = "application/json")
    public String login(@RequestBody AccountDTO accountDTO){
        return activeUserService.login(accountDTO);
    }

    @GetMapping(value = logout)
    public void logout(@RequestHeader(value = "Token") String token){
        activeUserService.logout(token);
    }

    @PostMapping(value = changePassword, consumes = "application/json")
    public void changePassword(@RequestHeader(value = "Token") String token, @RequestBody AccountPasswordDTO accountPasswordDTO){
        accountService.changePassword(token, accountPasswordDTO.getPassword());
    }
}
