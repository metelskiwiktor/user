package pl.wiktor.management.user.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.exception.AccountInvalidLoginException;
import pl.wiktor.management.user.exception.AccountInvalidPasswordException;
import pl.wiktor.management.user.exception.AccountInvalidTokenException;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.repository.AccountRepositoryJpa;
import pl.wiktor.management.user.repository.ActiveUserRepositoryJpa;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActiveUserServiceImplTest {

    @Mock
    private ActiveUserRepositoryJpa activeUserRepository;

    @Mock
    private AccountRepositoryJpa accountRepository;

    @InjectMocks
    @Spy
    private ActiveUserServiceImpl activeUserService;

    private AccountDTO accountDTO;

    @Before
    public void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setLogin("wiktor");
        accountDTO.setPassword("wiktorhaslo");
    }

    @Test
    public void accountSuccessfulLoggedIn(){
        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(Optional.of(true));
        when(accountRepository.existsAccountByLoginAndPassword(any(), any())).thenReturn(Optional.of(true));
        when(activeUserRepository.save(any())).thenReturn(null);

        String token = activeUserService.login(accountDTO);

        verify(activeUserRepository, times(1)).save(any());
        assertNotNull(token);
    }

    @Test
    public void accountLoginDenied(){
        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(Optional.of(false));

        Assertions.assertThrows(AccountInvalidLoginException.class, () -> activeUserService.login(accountDTO));


        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(Optional.of(true));
        when(accountRepository.existsAccountByLoginAndPassword(any(), any())).thenReturn(Optional.of(false));

        Assertions.assertThrows(AccountInvalidPasswordException.class, () -> activeUserService.login(accountDTO));
    }

    @Test
    public void accountLoggedOut(){
        String token = "token";
        when(activeUserRepository.existsActiveAccountByToken(token)).thenReturn(Optional.of(true));
        doNothing().when(activeUserRepository).deleteActiveAccountByToken(token);

        activeUserService.logout(token);

        verify(activeUserRepository, times(1)).deleteActiveAccountByToken(token);
    }

    @Test
    public void logoutWithBadCredentials(){
        String token = "token";
        when(activeUserRepository.existsActiveAccountByToken(token)).thenReturn(Optional.of(false));

        activeUserService.logout(token);

        Assertions.assertThrows(AccountInvalidTokenException.class, () -> activeUserService.logout(token));
    }
}