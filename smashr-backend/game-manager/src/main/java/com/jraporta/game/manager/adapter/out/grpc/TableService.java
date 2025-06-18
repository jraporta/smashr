package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.technical.api.proto.HelloReply;
import com.jraporta.technical.api.proto.HelloRequest;
import com.jraporta.technical.api.proto.SimpleGrpc;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TableService {

    private final SimpleGrpc.SimpleBlockingStub tableManagerStub;

    public TableService(@Qualifier("tableManagerStub") SimpleGrpc.SimpleBlockingStub tableManagerStub) {
        this.tableManagerStub = tableManagerStub;
    }

    public String pushSayHello(String name) {
        HelloRequest req = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply = tableManagerStub.sayHello(req);
        log.info("TableService reply:{}", reply.getMessage());
        return reply.getMessage();
    }

}
