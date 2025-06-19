package com.jraporta.user.application.usecase;

import com.jraporta.user.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserQueryServiceImpl userQueryService;

    @Test
    void userExists_ShouldReturnTrue_IfUserExists() {
        String userId = "existingUser";
        when(userRepository.userExists(userId)).thenReturn(true);

        assertTrue(userQueryService.userExists(userId));
        verify(userRepository, times(1)).userExists(userId);
    }

    @Test
    void userExists_ShouldReturnFales_IfUserNotExists() {
        String userId = "nonExistingUser";
        when(userRepository.userExists(userId)).thenReturn(false);

        assertFalse(userQueryService.userExists(userId));
        verify(userRepository, times(1)).userExists(userId);
    }
}