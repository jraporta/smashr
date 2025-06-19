package com.jraporta.user.integration;

import com.jraporta.technical.api.proto.UserServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubTestConfig {

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceStub(GrpcChannelFactory channels) {
        return UserServiceGrpc.newBlockingStub((channels.createChannel("user-service")));
    }

}
