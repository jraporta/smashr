package com.jraporta.user.adapter.in.grpc;

import com.jraporta.technical.api.proto.UserCheckReply;
import com.jraporta.technical.api.proto.UserCheckRequest;
import com.jraporta.technical.api.proto.UserServiceGrpc;
import com.jraporta.user.application.usecase.UserQueryService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GrpcUserServer extends UserServiceGrpc.UserServiceImplBase {

    private final UserQueryService userQueryService;

    @Override
    public void checkUserExists(UserCheckRequest request, StreamObserver<UserCheckReply> responseObserver) {
        UserCheckReply reply = UserCheckReply.newBuilder()
                        .setExists(
                                userQueryService.userExists(request.getUserId())
                        )
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
