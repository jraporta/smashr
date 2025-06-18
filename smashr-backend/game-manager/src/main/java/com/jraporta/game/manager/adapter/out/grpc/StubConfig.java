package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.technical.api.proto.SimpleGrpc;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubConfig {

    @Bean
    public SimpleGrpc.SimpleBlockingStub exampleStub(GrpcChannelFactory channels) {
        return SimpleGrpc.newBlockingStub(channels.createChannel("table-service"));
    }

    @Bean
    public TableServiceGrpc.TableServiceBlockingStub tableServiceStub(GrpcChannelFactory channels) {
        return TableServiceGrpc.newBlockingStub((channels.createChannel("table-service")));
    }


}
