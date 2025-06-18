package com.jraporta.table_manager.adapter.in.grpc;

import com.jraporta.table_manager.applicaton.usecase.TableQueryService;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrpcTableServerTest {

    @Mock
    private TableQueryService tableQueryService;

    @Mock
    private StreamObserver<TableCheckReply> responseObserver;

    @InjectMocks
    private GrpcTableServer grpcTableServer;

    @Test
    void checkTableExists_ShouldReturnTrue_WhenTableExists() {
        String tableId = "tableId";
        when(tableQueryService.tableExists(tableId)).thenReturn(true);

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        grpcTableServer.checkTableExists(req, responseObserver);

        TableCheckReply expectedReply = TableCheckReply.newBuilder().setExists(true).build();
        verify(responseObserver).onNext(expectedReply);
        verify(responseObserver).onCompleted();
    }

    @Test
    void checkTableExists_ShouldReturnFalse_WhenTableNotExists() {
        String tableId = "tableId";
        when(tableQueryService.tableExists(tableId)).thenReturn(false);

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        grpcTableServer.checkTableExists(req, responseObserver);

        TableCheckReply expectedReply = TableCheckReply.newBuilder().setExists(false).build();
        verify(responseObserver).onNext(expectedReply);
        verify(responseObserver).onCompleted();
    }
}