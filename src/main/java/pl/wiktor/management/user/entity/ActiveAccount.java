package pl.wiktor.management.user.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "active_accounts")
public class ActiveAccount {

    @Id
    @GeneratedValue
    private int id;
    @Column
    private String login;
    @Column
    private String token;

    public ActiveAccount() {
    }

    public ActiveAccount(String login, String token){
        this.login = login;
        this.token = token;
    }
}
