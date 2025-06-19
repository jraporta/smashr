package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.game.manager.domain.port.out.UserCheckerPort;
import com.jraporta.technical.api.proto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GrpcUserCheckerClient implements UserCheckerPort {

    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public boolean userExists(String userId) {
        UserCheckRequest req = UserCheckRequest.newBuilder().setUserId(userId).build();
        UserCheckReply reply = userServiceBlockingStub.checkUserExists(req);
        return reply.getExists();
    }
}
