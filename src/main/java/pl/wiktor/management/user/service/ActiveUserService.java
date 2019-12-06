package pl.wiktor.management.user.service;

import pl.wiktor.management.user.model.dto.request.AccountDTO;

public interface ActiveUserService {
    String login(AccountDTO accountDTO);
    void logout(String token);
}
