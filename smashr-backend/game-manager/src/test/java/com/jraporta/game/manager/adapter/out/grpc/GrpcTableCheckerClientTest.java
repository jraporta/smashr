package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GrpcTableCheckerClientTest {

    @Mock
    private TableServiceGrpc.TableServiceBlockingStub tableServiceBlockingStub;

    @InjectMocks
    private GrpcTableCheckerClient grpcTableCheckerClient;

    @Test
    void tableExists_ShouldReturnTrue_IfTableExists() {
        String tableId = "tableId";

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        when(tableServiceBlockingStub.checkTableExists(req)).thenReturn(TableCheckReply.newBuilder().setExists(true).build());

        assertTrue(grpcTableCheckerClient.tableExists(tableId));
        verify(tableServiceBlockingStub).checkTableExists(req);
    }

    @Test
    void tableExists_ShouldReturnFalse_IfTableNotExists() {
        String tableId = "tableId";

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        when(tableServiceBlockingStub.checkTableExists(req)).thenReturn(TableCheckReply.newBuilder().setExists(false).build());

        assertFalse(grpcTableCheckerClient.tableExists(tableId));
        verify(tableServiceBlockingStub).checkTableExists(req);
    }
}