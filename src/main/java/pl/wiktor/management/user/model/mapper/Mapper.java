package pl.wiktor.management.user.model.mapper;

import pl.wiktor.management.user.model.dto.response.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;

public class Mapper {
    public static Account map(AccountDTO accountDTO){
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setLogin(accountDTO.getLogin());
        account.setPassword(account.getPassword());
        return account;
    }
}
