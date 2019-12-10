package pl.wiktor.management.user.controller.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wiktor.management.user.controller.impl.AccountControllerImpl;
import pl.wiktor.management.user.model.dto.request.AccountPasswordDTO;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.service.impl.AccountServiceImpl;
import pl.wiktor.management.user.service.impl.ActiveUserServiceImpl;

import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerImplTest {
    @Mock
    private AccountServiceImpl accountService;

    @Mock
    private ActiveUserServiceImpl activeUserService;

    @InjectMocks
    @Spy
    private AccountControllerImpl accountController;


    private AccountDTO accountDTO;
    private AccountPasswordDTO accountPasswordDTO;
    private String token = "token";

    @Before
    public void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setLogin("wiktor");
        accountDTO.setPassword("wiktorhaslo");

        accountPasswordDTO = new AccountPasswordDTO();
        accountPasswordDTO.setPassword("password");
    }

    @Test
    public void register() {
        doNothing().when(accountService).register(accountDTO);
        accountController.register(accountDTO);
        verify(accountService, times(1)).register(accountDTO);
    }

    @Test
    public void login() {
        when(activeUserService.login(accountDTO)).thenReturn(token);
        String result = accountController.login(accountDTO);
        assertEquals(token, result);
    }

    @Test
    public void logout() {
        doNothing().when(activeUserService).logout(token);
        accountController.logout(token);
        verify(activeUserService, times(1)).logout(token);
    }

    @Test
    public void changePassword() {
        doNothing().when(accountService).changePassword(token, accountPasswordDTO.getPassword());
        accountController.changePassword(token, accountPasswordDTO);
        verify(accountService, times(1)).changePassword(token, accountPasswordDTO.getPassword());
    }
}