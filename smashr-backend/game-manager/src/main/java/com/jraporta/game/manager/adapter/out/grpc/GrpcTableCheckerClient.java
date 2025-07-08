package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.game.manager.application.exception.QueryServiceInternalException;
import com.jraporta.game.manager.application.exception.TableNotFoundException;
import com.jraporta.game.manager.application.port.out.TableQueryService;
import com.jraporta.game.manager.application.model.TableDetails;
import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableDetailsReply;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
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
        try {
            TableDetailsReply reply = tableServiceStub.getTableDetails(req);
            return new TableDetails(tableId, reply.getName(), reply.getDescription());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                log.warn("Table with id {} not found", tableId, e);
                throw new TableNotFoundException(String.format("Table with id %s not found", tableId));
            }
            log.error("Error loading details of table {}", tableId, e);
            throw new QueryServiceInternalException("Unable to recover the details for table with id " + tableId);
        }
    }
}
