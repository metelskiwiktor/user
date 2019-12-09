package pl.wiktor.management.user.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.repository.AccountRepositoryJpa;
import pl.wiktor.management.user.repository.ActiveUserRepositoryJpa;

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
        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(true);
        when(accountRepository.existsAccountByLoginAndPassword(any(), any())).thenReturn(true);
        when(activeUserRepository.save(any())).thenReturn(null);

        String token = activeUserService.login(accountDTO);

        verify(activeUserRepository, times(1)).save(any());
        assertNotNull(token);
    }

    @Test
    public void accountLoginDenied(){
        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        String token = activeUserService.login(accountDTO);
        assertNull(token);
        verify(activeUserRepository, times(0)).save(any());

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(true);
        when(accountRepository.existsAccountByLoginAndPassword(any(), any())).thenReturn(false);

        token = activeUserService.login(accountDTO);
        assertNull(token);
        verify(activeUserRepository, times(0)).save(any());
    }


    @Test
    public void accountLoggedOut(){
        String token = "token";
        when(activeUserRepository.existsActiveAccountByToken(token)).thenReturn(true);
        doNothing().when(activeUserRepository).deleteActiveAccountByToken(token);

        activeUserService.logout(token);

        verify(activeUserRepository, times(1)).deleteActiveAccountByToken(token);
    }

    @Test
    public void logoutWithBadCredentials(){
        String token = "token";
        when(activeUserRepository.existsActiveAccountByToken(token)).thenReturn(false);

        activeUserService.logout(token);

        verify(activeUserRepository, times(0)).deleteActiveAccountByToken(token);
    }
}