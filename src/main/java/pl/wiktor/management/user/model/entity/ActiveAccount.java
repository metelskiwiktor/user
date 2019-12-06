package pl.wiktor.management.user.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "active_accounts")
public class ActiveAccount {

    @Id
    private String login;
    @Column
    private String token;

    public ActiveAccount() {
    }

    public ActiveAccount(String login, String token){
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }
}
