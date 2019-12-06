package pl.wiktor.management.user.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.helper.QueryHelper;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.entity.ActiveAccount;
import pl.wiktor.management.user.model.enums.TableSearcher;
import pl.wiktor.management.user.repository.impl.ActiveUserRepositoryImpl;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActiveUserRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private QueryHelper queryHelper;

    @Spy
    @InjectMocks
    private ActiveUserRepositoryImpl activeUserRepository;

    private Account account;

    @Before
    public void setUp() {
        account = new Account("wiktor","wiktorhaslo");
    }

    @Test
    public void login() throws AccountLoginException {
        String token = "token";
        when(queryHelper.isAccountInDb(any(TableSearcher.class), anyString())).thenReturn(false);
        activeUserRepository.login(account.getLogin(), token);
        verify(entityManager, times(1)).merge(any(ActiveAccount.class));
    }

    @Test
    public void loginShouldThrowException() {
        String token = "token";
        when(queryHelper.isAccountInDb(any(TableSearcher.class), anyString())).thenReturn(true);
        Assertions.assertThrows(AccountLoginException.class, () -> activeUserRepository.login(account.getLogin(), token));
        verify(entityManager, times(0)).merge(any(ActiveAccount.class));
    }

    @Test
    public void logout() {
        String token = "token";
        doNothing().when(queryHelper).deleteAccountFromActiveAccount(token);
        activeUserRepository.logout(token);
        verify(entityManager, times(1)).flush();
    }

    @Test
    public void isAccountLoggedIn() {
        when(queryHelper.isAccountInDb(any(TableSearcher.class), anyString())).thenReturn(true);
        boolean result = activeUserRepository.isAccountLoggedIn(TableSearcher.AccountByLogin, "fieldValue");
        assertTrue(result);
    }
}
