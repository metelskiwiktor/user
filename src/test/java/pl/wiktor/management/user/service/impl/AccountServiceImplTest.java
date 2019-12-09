package pl.wiktor.management.user.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.entity.ActiveAccount;
import pl.wiktor.management.user.repository.AccountRepositoryJpa;
import pl.wiktor.management.user.repository.ActiveUserRepositoryJpa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepositoryJpa accountRepository;

    @Mock
    private ActiveUserRepositoryJpa activeUserRepository;

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
    public void accountSuccessfulRegistered() {
        when(accountRepository.existsAccountByLogin(any())).thenReturn(false);
        when(accountRepository.save(any())).thenReturn(null);

        boolean result = accountService.register(accountDTO);

        assertTrue(result);
    }

    @Test
    public void accountAlreadyRegistered() {
        when(accountRepository.existsAccountByLogin(any())).thenReturn(true);

        boolean result = accountService.register(accountDTO);

        assertFalse(result);
    }

    @Test
    public void passwordSuccessfulChanged() {
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

    @Test
    public void accountMustBeLoggedInToChangePassword(){
        String token = "token";
        String newPassword = "noweHaslo";

        when(activeUserRepository.existsActiveAccountByToken(token)).thenReturn(false);

        accountService.changePassword(token, newPassword);

        verify(accountRepository, times(0)).save(any());
    }
}