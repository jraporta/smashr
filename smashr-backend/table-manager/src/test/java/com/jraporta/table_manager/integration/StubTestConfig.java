package com.jraporta.table_manager.integration;

import com.jraporta.technical.api.proto.TableServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubTestConfig {

    @Bean
    public TableServiceGrpc.TableServiceBlockingStub tableServiceStub(GrpcChannelFactory channels) {
        return TableServiceGrpc.newBlockingStub((channels.createChannel("table-service")));
    }


}
