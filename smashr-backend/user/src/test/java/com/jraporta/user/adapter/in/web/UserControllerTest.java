package com.jraporta.user.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jraporta.user.adapter.in.web.dto.LoginRequest;
import com.jraporta.user.application.usecase.LoginUseCase;
import com.jraporta.user.application.usecase.exception.BadCredentialsException;
import com.jraporta.user.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void login_whenUsernameIsProvided_shouldReturnUser() throws Exception {
        String username = "testUser";
        String password = "1234";
        LoginRequest request = new LoginRequest(username, password);
        User user = new User("someId", username, "test@mail.com", password);
        when(loginUseCase.login(username, password)).thenReturn(user);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(user), JsonCompareMode.STRICT));

        verify(loginUseCase, times(1)).login(username, password);
    }

    @Test
    void login_whenEmailIsProvided_shouldReturnUser() throws Exception {
        String username = "testUser@mail.com";
        String password = "1234";
        LoginRequest request = new LoginRequest(username, password);
        User user = new User("someId", "someUsername", username, password);
        when(loginUseCase.login(username, password)).thenReturn(user);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(user), JsonCompareMode.STRICT));

        verify(loginUseCase, times(1)).login(username, password);
    }

    @Test
    void login_whenBadCredentialsProvided_shouldReturn401() throws Exception {
        String username = "testUser";
        String password = "1234";
        String errorMessage = "Error message";
        LoginRequest request = new LoginRequest(username, password);
        when(loginUseCase.login(username, password)).thenThrow(new BadCredentialsException(errorMessage));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(errorMessage));

        verify(loginUseCase, times(1)).login(username, password);
    }

}