package com.jraporta.table_manager.adapter.in.grpc;

import com.jraporta.table_manager.domain.port.out.TableRepository;
import com.jraporta.technical.api.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class GrpcTableServer extends TableServiceGrpc.TableServiceImplBase {

    private final TableRepository tableRepository;

    @Override
    public void checkTableExists(TableCheckRequest request, StreamObserver<TableCheckReply> responseObserver) {
        TableCheckReply reply = TableCheckReply.newBuilder()
                .setExists(tableRepository.tableExists(request.getTableId()))
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
