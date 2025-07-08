package com.jraporta.game.manager.adapter.out.grpc;

import com.jraporta.game.manager.application.exception.QueryServiceInternalException;
import com.jraporta.game.manager.application.exception.TableNotFoundException;
import com.jraporta.game.manager.application.model.TableDetails;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableDetailsReply;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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

    @Test
    void getTableDetails_ShouldReturnDetails_IfTableExists() {
        String tableId = "tableId";

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        TableDetailsReply expectedReply = TableDetailsReply.newBuilder().setName("table1").setDescription("description").build();
        when(tableServiceBlockingStub.getTableDetails(req)).thenReturn(expectedReply);

        TableDetails result = grpcTableCheckerClient.getTableDetails(tableId);

        assertEquals(expectedReply.getName(), result.name());
        assertEquals(expectedReply.getDescription(), result.description());

        verify(tableServiceBlockingStub).getTableDetails(req);
    }

    @Test
    void getTableDetails_ShouldThrowTableNotFoundException_IfTableNotFound() {
        String tableId = "nonExistingId";

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        when(tableServiceBlockingStub.getTableDetails(req)).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));

        assertThrows(TableNotFoundException.class, () -> grpcTableCheckerClient.getTableDetails(tableId));

        verify(tableServiceBlockingStub).getTableDetails(req);
    }

    @Test
    void getTableDetails_ShouldThrowQueryServiceInternalException_IfUnexpectedError() {
        String tableId = "tableId";

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        when(tableServiceBlockingStub.getTableDetails(req)).thenThrow(new StatusRuntimeException(Status.INTERNAL));

        assertThrows(QueryServiceInternalException.class, () -> grpcTableCheckerClient.getTableDetails(tableId));

        verify(tableServiceBlockingStub).getTableDetails(req);
    }
}