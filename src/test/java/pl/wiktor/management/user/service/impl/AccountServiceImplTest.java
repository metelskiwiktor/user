package pl.wiktor.management.user.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.exception.AccountLoginException;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.entity.ActiveAccount;
import pl.wiktor.management.user.repository.AccountRepository;
import pl.wiktor.management.user.repository.ActiveUserRepository;
import pl.wiktor.management.user.service.impl.AccountServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ActiveUserRepository activeUserRepository;

    @InjectMocks
    @Spy
    private AccountServiceImpl accountService;

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
        when(activeUserRepository.existsActiveAccountByLogin(any())).thenReturn(false);
        when(accountRepository.save(any())).thenReturn(null);
        final boolean result = accountService.register(accountDTO);
        assertTrue(result);
    }

    @Test
    public void registerShouldThrowException() throws AccountLoginException {
        when(activeUserRepository.existsActiveAccountByLogin(any())).thenReturn(true);
        final boolean result = accountService.register(accountDTO);
        assertFalse(result);
    }
//
    @Test
    public void changePassword() {
        Account account = new Account("wiktor","haslo");
        String token = "token";
        String newPassword = "nowehaslo";
        when(activeUserRepository.existsActiveAccountByToken(token)).thenReturn(true);
        when(activeUserRepository.getActiveAccountByToken(token)).thenReturn(new ActiveAccount("wiktor",token));
        when(accountRepository.getAccountByLogin(accountDTO.getLogin())).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(null);
        accountService.changePassword(token, newPassword);
        verify(accountRepository, times(1)).save(account);
    }
}