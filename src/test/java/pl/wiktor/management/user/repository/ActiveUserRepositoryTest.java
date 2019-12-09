package pl.wiktor.management.user.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wiktor.management.user.model.entity.ActiveAccount;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ActiveUserRepositoryTest {
    private static final String INVALID_TOKEN = "InvalidToken";
    private static final String INVALID_LOGIN = "InvalidLogin";

    @Autowired
    private ActiveUserRepositoryJpa activeUserRepository;

    private ActiveAccount activeAccount;

    @Before
    public void setUp() {
        activeAccount = new ActiveAccount("wiktor","wiktorstoken");
        activeUserRepository.save(activeAccount);
    }

    @Test
    public void getActiveAccountByToken(){
        ActiveAccount activeAccountResult = activeUserRepository.getActiveAccountByToken(activeAccount.getToken());

        assertEquals(activeAccount.getLogin(), activeAccountResult.getLogin());
        assertEquals(activeAccount.getToken(), activeAccountResult.getToken());
    }

    @Test
    public void existsActiveAccountByToken(){
        boolean shouldBeTrue = activeUserRepository.existsActiveAccountByToken(activeAccount.getToken());
        boolean shouldBeFalse = activeUserRepository.existsActiveAccountByToken(INVALID_TOKEN);

        assertTrue(shouldBeTrue);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void existsActiveAccountByLogin(){
        boolean shouldBeTrue = activeUserRepository.existsActiveAccountByLogin(activeAccount.getLogin());
        boolean shouldBeFalse = activeUserRepository.existsActiveAccountByLogin(INVALID_LOGIN);

        assertTrue(shouldBeTrue);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void deleteActiveAccountByToken(){
        assertTrue(activeUserRepository.findAll().size() > 0);
        activeUserRepository.deleteActiveAccountByToken(activeAccount.getToken());
        assertEquals(0, activeUserRepository.findAll().size());
    }

}
