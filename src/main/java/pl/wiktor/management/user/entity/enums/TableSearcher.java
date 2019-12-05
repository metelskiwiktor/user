package pl.wiktor.management.user.entity.enums;

import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.ActiveAccount;

public enum TableSearcher {
    AccountByLogin(Account.class.getSimpleName(), "login"),
    ActiveAccountByLogin(ActiveAccount.class.getSimpleName(), "login"),
    ActiveAccountByToken(ActiveAccount.class.getSimpleName(), "token");

    private String table;
    private String field;

    TableSearcher(String tableClass, String field) {
        this.table = tableClass;
        this.field = field;
    }

    public String getTable() {
        return table;
    }

    public String getField() {
        return field;
    }
}
