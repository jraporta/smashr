package com.jraporta.user.adapter.in.grpc;

import com.jraporta.technical.api.proto.UserCheckReply;
import com.jraporta.technical.api.proto.UserCheckRequest;
import com.jraporta.user.application.usecase.UserQueryService;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GrpcUserServerTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private StreamObserver<UserCheckReply> responseObserver;

    @InjectMocks
    private GrpcUserServer grpcUserServer;

    @Test
    void checkUserExists_ShouldReturnTrue_WhenUserExists() {
        String userId = "existingUser";
        when(userQueryService.userExists(userId)).thenReturn(true);

        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();
        grpcUserServer.checkUserExists(req, responseObserver);

        UserCheckReply expectedReply = UserCheckReply.newBuilder().setExists(true).build();
        verify(responseObserver).onNext(expectedReply);
        verify(responseObserver).onCompleted();
    }

    @Test
    void checkUserExists_ShouldReturnFalse_WhenUserNotExists() {
        String userId = "nonExistingUser";
        when(userQueryService.userExists(userId)).thenReturn(false);

        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();
        grpcUserServer.checkUserExists(req, responseObserver);

        UserCheckReply expectedReply = UserCheckReply.newBuilder().setExists(false).build();
        verify(responseObserver).onNext(expectedReply);
        verify(responseObserver).onCompleted();
    }
}