package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.technical.api.proto.UserCheckReply;
import com.jraporta.technical.api.proto.UserCheckRequest;
import com.jraporta.technical.api.proto.UserServiceGrpc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GrpcUserCheckerClientTest {

    @Mock
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @InjectMocks
    private GrpcUserCheckerClient grpcUserCheckerClient;

    @Test
    void userExists_ShouldReturnTrue_IfUserExists() {
        String userId = "existingUser";

        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();
        when(userServiceBlockingStub.checkUserExists(req)).thenReturn(UserCheckReply.newBuilder().setExists(true).build());

        assertTrue(grpcUserCheckerClient.userExists(userId));
        verify(userServiceBlockingStub, times(1)).checkUserExists(req);
    }

    @Test
    void userExists_ShouldReturnFalse_IfUserNotExists() {
        String userId = "nonExistingUser";

        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();
        when(userServiceBlockingStub.checkUserExists(req)).thenReturn(UserCheckReply.newBuilder().setExists(false).build());

        assertFalse(grpcUserCheckerClient.userExists(userId));
        verify(userServiceBlockingStub, times(1)).checkUserExists(req);
    }
}