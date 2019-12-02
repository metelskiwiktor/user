package pl.wiktor.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.Token;
import pl.wiktor.management.user.repository.UserRepository;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AccountController {
    private final static String login = "login/";
    private final static String register = "register/";
    private final static String account = "account/";
//
//    private UserRepository userRepository;
//    private ActiveUserRepository activeUserRepository;
//
//    @Autowired
//    public AccountController(UserRepository userRepository, ActiveUserRepository activeUserRepository) {
//        this.userRepository = userRepository;
//        this.activeUserRepository = activeUserRepository;
//    }
//
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
//
//    @PostMapping(value = register, consumes = "application/json")
//    public void register(@RequestBody Account account){
//        userRepository.addUser(account);
//    }
//
//    @PostMapping(value = account, consumes = "application/x-www-form-urlencoded")
//    public void changePassword(
//            @RequestHeader(name = "TokenAuthorization") String token,
//            @RequestParam() String newPassword){
//        Account account = activeUserRepository.getUserByToken(token);
//        userRepository.changePassword(account, newPassword);
//    }
//
//    @GetMapping(account)
//    public List<Account> users(){
//        return userRepository.getAccounts();
//    }
//
//    @GetMapping("activeUsers")
//    public Map<Account, Token> activeUsers(){
//        return activeUserRepository.getActiveUsers();
//    }
}
