package pl.wiktor.management.user.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wiktor.management.user.model.entity.Account;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    private static final String INVALID_LOGIN = "InvalidLogin";
    private static final String INVALID_PASSWORD = "InvalidPassword";

    @Autowired
    private AccountRepositoryJpa accountRepository;

    private Account account;


    @Before
    public void setUp() {
        account = new Account("wiktor","haslo");
        accountRepository.save(account);

    }

    @Test
    public void existsAccountByLogin(){
        boolean shouldBeTrue = accountRepository.existsAccountByLogin(account.getLogin()).get();
        boolean shouldBeFalse = accountRepository.existsAccountByLogin(INVALID_LOGIN).get();

        assertTrue(shouldBeTrue);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void getAccountByLogin(){
        Account accountResult = accountRepository.getAccountByLogin(this.account.getLogin());

        assertEquals(this.account, accountResult);
    }

    @Test
    public void existsAccountByLoginAndPassword(){
        boolean shouldBeTrue = accountRepository.existsAccountByLoginAndPassword(account.getLogin(), account.getPassword()).get();
        boolean shouldBeFalse = accountRepository.existsAccountByLoginAndPassword(INVALID_LOGIN, INVALID_PASSWORD).get();

        assertTrue(shouldBeTrue);
        assertFalse(shouldBeFalse);
    }
}
