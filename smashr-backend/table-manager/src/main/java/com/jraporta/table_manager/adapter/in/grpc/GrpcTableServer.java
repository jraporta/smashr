package com.jraporta.table_manager.adapter.in.grpc;

import com.jraporta.table_manager.applicaton.usecase.TableQueryService;
import com.jraporta.table_manager.domain.exception.TableNotFoundException;
import com.jraporta.table_manager.domain.model.table.Table;
import com.jraporta.technical.api.proto.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class GrpcTableServer extends TableServiceGrpc.TableServiceImplBase {

    private final TableQueryService tableQueryService;

    @Override
    public void checkTableExists(TableCheckRequest request, StreamObserver<TableCheckReply> responseObserver) {
        TableCheckReply reply = TableCheckReply.newBuilder()
                .setExists(tableQueryService.tableExists(request.getTableId()))
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getTableDetails(TableCheckRequest request, StreamObserver<TableDetailsReply> responseObserver) {
        try {
            Table table = tableQueryService.getTable(request.getTableId());
            TableDetailsReply reply = TableDetailsReply.newBuilder()
                    .setName(table.getName() != null ? table.getName() : "")
                    .setDescription(table.getDescription() != null ? table.getDescription() : "")
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            log.info("Table details sent: {}", request.getTableId());
        } catch (TableNotFoundException e) {
            log.warn("Table not found: {}", request.getTableId());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Table with id " + request.getTableId() + " not found")
                            .asRuntimeException()
            );
        } catch (Exception e) {
            log.error("Unexpected error while getting table details for id: {}", request.getTableId(), e);
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Internal server error")
                            .withCause(e)
                            .asRuntimeException()
            );
        }

    }
}
