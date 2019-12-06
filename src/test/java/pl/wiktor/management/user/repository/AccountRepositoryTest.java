package pl.wiktor.management.user.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.helper.QueryHelper;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.enums.TableSearcher;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private QueryHelper queryHelper;

    @Spy
    @InjectMocks
    private AccountRepository accountRepository;

    private Account account;

    @Before
    public void setUp() {
        account = new Account("wiktor", "wiktorhaslo");

    }

    @Test
    public void addAccount() throws AccountLoginException {
        when(queryHelper.isAccountInDb(any(TableSearcher.class), anyString())).thenReturn(false);
        accountRepository.addAccount(account);
        verify(accountRepository, times(1)).addAccount(account);
    }

    @Test
    public void addAccountThrowsExceptionWhenIsRegistered() {
        when(queryHelper.isAccountInDb(any(TableSearcher.class), anyString())).thenReturn(true);
        assertThrows(AccountLoginException.class, () -> accountRepository.addAccount(account));
    }

    @Test
    public void changePassword(){
        String newPassword = "wiktornowehaslo";
        String token = "token";
        when(queryHelper.getAccountByToken(token)).thenReturn(account);
        accountRepository.changePassword(token,newPassword);
        assertEquals(newPassword, account.getPassword());
    }


    @Test
    public void isAccountExistWithPassword() {
        when(queryHelper.isAccountExistWithPassword(account.getLogin(),account.getPassword())).thenReturn(true);
        boolean result = accountRepository.isAccountExistWithPassword(account.getLogin(), account.getPassword());
        assertTrue(result);
    }

    @Test
    public void isLoginInDb() {
        when(queryHelper.isAccountInDb(TableSearcher.AccountByLogin, account.getLogin())).thenReturn(true);
        boolean result = accountRepository.isLoginInDb(account.getLogin());
        assertTrue(result);
    }

}