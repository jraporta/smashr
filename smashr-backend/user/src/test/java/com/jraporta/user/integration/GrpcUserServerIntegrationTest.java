package com.jraporta.user.integration;

import com.jraporta.technical.api.proto.UserCheckReply;
import com.jraporta.technical.api.proto.UserCheckRequest;
import com.jraporta.technical.api.proto.UserServiceGrpc;
import com.jraporta.user.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(StubTestConfig.class)
class GrpcUserServerIntegrationTest {

    @Autowired
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void shouldReturnExistsTrue_WhenUserExists() {
        String userId = "existingUser";

        when(userRepository.userExists(userId)).thenReturn(true);

        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();

        UserCheckReply reply = userServiceBlockingStub.checkUserExists(req);

        assertTrue(reply.getExists());
        verify(userRepository,times(1)).userExists(userId);
    }

    @Test
    void shouldReturnExistsFalse_WhenUserNotExists() {
        String userId = "nonExistingUser";

        when(userRepository.userExists(userId)).thenReturn(false);

        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();

        UserCheckReply reply = userServiceBlockingStub.checkUserExists(req);

        assertFalse(reply.getExists());
        verify(userRepository,times(1)).userExists(userId);
    }
}