package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.technical.api.proto.HelloReply;
import com.jraporta.technical.api.proto.HelloRequest;
import com.jraporta.technical.api.proto.SimpleGrpc;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @deprecated (example code, just for reference)
 */
@Deprecated(forRemoval = true)
@Slf4j
@AllArgsConstructor
@Service
public class TableService {

    private final SimpleGrpc.SimpleBlockingStub tableManagerStub;

    public String pushSayHello(String name) {
        HelloRequest req = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply = tableManagerStub.sayHello(req);
        log.info("TableService reply:{}", reply.getMessage());
        return reply.getMessage();
    }

}
