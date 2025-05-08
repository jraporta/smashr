package com.jraporta.user.application.usecase;

import com.jraporta.user.application.usecase.exception.BadCredentialsException;
import com.jraporta.user.domain.model.user.User;
import com.jraporta.user.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @Test
    void login_existsUserWithGivenUsername_shouldReturnUser() {
        String username = "testUser";
        String password = "1234";
        User user = new User("someId", username, "someEmail", password);
        when(userRepository.findByEmailOrUsername(username)).thenReturn(Optional.of(user));

        assertEquals(user, loginService.login(username, password));

        verify(userRepository, times(1)).findByEmailOrUsername(username);
    }

    @Test
    void login_existsUserWithGivenEmail_shouldReturnUser() {
        String username = "testUser@mail.com";
        String password = "1234";
        User user = new User("someId", "someUsername", username, password);
        when(userRepository.findByEmailOrUsername(username)).thenReturn(Optional.of(user));

        assertEquals(user, loginService.login(username, password));

        verify(userRepository, times(1)).findByEmailOrUsername(username);
    }

    @Test
    void login_noUserWithGivenUsername_shouldThrowBadCredentialsException() {
        String username = "testUser";
        String password = "BadPassword";
        when(userRepository.findByEmailOrUsername(username)).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> loginService.login(username, password));

        verify(userRepository, times(1)).findByEmailOrUsername(username);
    }

    @Test
    void login_badPassword_shouldThrowBadCredentialsException() {
        String username = "testUser";
        String password = "BadPassword";
        User user = new User("someId", username, "someEmail", "1234");
        when(userRepository.findByEmailOrUsername(username)).thenReturn(Optional.of(user));

        assertThrows(BadCredentialsException.class, () -> loginService.login(username, password));

        verify(userRepository, times(1)).findByEmailOrUsername(username);
    }
}