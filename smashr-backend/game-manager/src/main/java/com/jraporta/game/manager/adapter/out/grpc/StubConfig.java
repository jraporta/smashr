package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.technical.api.proto.SimpleGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubConfig {

    @Bean
    public SimpleGrpc.SimpleBlockingStub tableManagerStub(GrpcChannelFactory channels) {
        return SimpleGrpc.newBlockingStub(channels.createChannel("tableManager"));
    }

}
