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
import pl.wiktor.management.user.repository.AccountRepositoryImpl;
import pl.wiktor.management.user.repository.ActiveUserRepository;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActiveUserServiceTest {

    @Mock
    private ActiveUserRepository activeUserRepository;

    @Mock
    private AccountRepositoryImpl accountRepository;

    @InjectMocks
    @Spy
    private ActiveUserService activeUserService;

    private AccountDTO accountDTO;

    @Before
    public void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setLogin("wiktor");
        accountDTO.setPassword("wiktorhaslo");
    }

    @Test
    public void login() throws AccountLoginException {
        when(accountRepository.isLoginInDb(anyString())).thenReturn(true);
        when(accountRepository.isAccountExistWithPassword(any(), any())).thenReturn(true);
        doNothing().when(activeUserRepository).login(anyString(), anyString());

        final String token = activeUserService.login(accountDTO);

        assertNotNull(token);
        assertEquals(UUID.randomUUID().toString().length(), token.length());
    }

    @Test
    public void InvalidLoginShouldCatchAccountLoginException() throws AccountLoginException {
        when(accountRepository.isLoginInDb(anyString())).thenReturn(true);
        when(accountRepository.isAccountExistWithPassword(any(), any())).thenReturn(true);
        doThrow(new AccountLoginException()).when(activeUserRepository).login(anyString(), anyString());

        final String token = activeUserService.login(accountDTO);

        assertNull(token);
    }

    @Test
    public void InvalidPasswordShouldCatchAccountPasswordException() {
        when(accountRepository.isLoginInDb(anyString())).thenReturn(true);
        when(accountRepository.isAccountExistWithPassword(any(), any())).thenReturn(false);

        final String token = activeUserService.login(accountDTO);

        assertNull(token);
    }

    @Test
    public void logout(){
        when(activeUserRepository.isAccountLoggedIn(any(), any())).thenReturn(true);
        doNothing().when(activeUserRepository).logout(anyString());
        activeUserService.logout("token");
        verify(activeUserRepository, times(1)).logout(anyString());
    }

    @Test
    public void logoutWithBadCredentials(){
        when(activeUserRepository.isAccountLoggedIn(any(), any())).thenReturn(false);
        activeUserService.logout("token");
        verify(activeUserRepository, times(0)).logout(anyString());
    }
}