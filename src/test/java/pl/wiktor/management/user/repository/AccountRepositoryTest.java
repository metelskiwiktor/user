package pl.wiktor.management.user.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.entity.Account;
import pl.wiktor.management.user.entity.enums.TableSearcher;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.helper.QueryHelper;

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
    //addaccount - done
    //changepassword - done


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





}