package pl.wiktor.management.user.controller;

import pl.wiktor.management.user.model.dto.request.AccountPasswordDTO;
import pl.wiktor.management.user.model.dto.request.AccountDTO;

public interface AccountController {
    boolean register(AccountDTO accountDTO);
    String login(AccountDTO accountDTO);
    void logout(String token);
    void changePassword(String token, AccountPasswordDTO accountPasswordDTO);
}
