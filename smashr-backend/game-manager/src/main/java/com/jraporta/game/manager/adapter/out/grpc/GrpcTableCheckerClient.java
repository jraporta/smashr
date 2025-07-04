package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.game.manager.application.port.out.TableQueryService;
import com.jraporta.game.manager.application.model.TableDetails;
import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableDetailsReply;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GrpcTableCheckerClient implements TableCheckerPort, TableQueryService {

    private final TableServiceGrpc.TableServiceBlockingStub tableServiceStub;

    @Override
    public boolean tableExists(String tableId) {
        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        TableCheckReply reply = tableServiceStub.checkTableExists(req);
        return reply.getExists();
    }

    @Override
    public TableDetails getTableDetails(String tableId) {
        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        TableDetailsReply reply = tableServiceStub.getTableDetails(req);
        return new TableDetails(tableId, reply.getName(), reply.getDescription());
    }
}
