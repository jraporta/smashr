package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GrpcTableCheckerClient implements TableCheckerPort {

    private final TableServiceGrpc.TableServiceBlockingStub tableServiceStub;

    @Override
    public boolean tableExists(String tableId) {
        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        TableCheckReply reply = tableServiceStub.checkTableExists(req);
        return reply.getExists();
    }
}
