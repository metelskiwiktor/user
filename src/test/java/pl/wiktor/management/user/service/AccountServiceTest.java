package pl.wiktor.management.user.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.model.dto.response.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.enums.TableSearcher;
import pl.wiktor.management.user.repository.AccountRepository;
import pl.wiktor.management.user.repository.ActiveUserRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ActiveUserRepository activeUserRepository;

    @InjectMocks
    @Spy
    private AccountService accountService;

    private AccountDTO accountDTO;

    @Before
    public void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setLogin("wiktor");
        accountDTO.setPassword("wiktorhaslo");
    }

    @Test
    public void register() throws AccountLoginException {
        doNothing().when(accountRepository).addAccount(any(Account.class));
        final boolean result = accountService.register(accountDTO);
        assertTrue(result);
    }

    @Test
    public void registerShouldThrowException() throws AccountLoginException {
        doThrow(new AccountLoginException("")).when(accountRepository).addAccount(any(Account.class));
        final boolean result = accountService.register(accountDTO);
        assertFalse(result);
    }

    @Test
    public void changePassword() {
        String token = "token";
        String newPassword = "nowehaslo";
        when(activeUserRepository.isAccountLoggedIn(any(TableSearcher.class), anyString())).thenReturn(true);
        doNothing().when(accountRepository).changePassword(eq(token), eq(newPassword));
        accountService.changePassword(token, newPassword);
        verify(accountRepository, times(1)).changePassword(eq(token), eq(newPassword));
    }
}