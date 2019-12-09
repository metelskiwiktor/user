package pl.wiktor.management.user.service;

import pl.wiktor.management.user.model.dto.request.AccountDTO;

public interface AccountService {
    void register(AccountDTO accountDTO);
    void changePassword(String token, String newPassword);
}
