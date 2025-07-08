package com.jraporta.table_manager.adapter.in.grpc;

import com.jraporta.table_manager.applicaton.usecase.TableQueryService;
import com.jraporta.table_manager.domain.exception.TableNotFoundException;
import com.jraporta.table_manager.domain.model.table.Table;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableDetailsReply;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrpcTableServerTest {

    @Mock
    private TableQueryService tableQueryService;

    @Mock
    private StreamObserver<TableCheckReply> responseObserver;

    @Mock
    private StreamObserver<TableDetailsReply> detailsResponseObserver;

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

    @Test
    void getTableDetails_ShouldReturnDetails_WhenTableExists() {
        String tableId = "tableId";
        String name = "name";
        String description = "description";
        TableDetailsReply expectedReply = TableDetailsReply.newBuilder().setName(name).setDescription(description).build();
        when(tableQueryService.getTable(tableId)).thenReturn(new Table(tableId, name, description));

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        grpcTableServer.getTableDetails(req, detailsResponseObserver);

        verify(detailsResponseObserver).onNext(expectedReply);
        verify(detailsResponseObserver).onCompleted();

        verify(tableQueryService, times(1)).getTable(tableId);
    }

    @Test
    void getTableDetails_ShouldReturnNotFoundStatus_WhenTableNotFound() {
        String tableId = "tableId";
        when(tableQueryService.getTable(tableId)).thenThrow(new TableNotFoundException(""));

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        grpcTableServer.getTableDetails(req, detailsResponseObserver);

        ArgumentCaptor<Throwable> captor = ArgumentCaptor.forClass(Throwable.class);
        verify(detailsResponseObserver).onError(captor.capture());

        Throwable thrown = captor.getValue();
        assertInstanceOf(StatusRuntimeException.class, thrown);
        StatusRuntimeException statusEx = (StatusRuntimeException) thrown;
        assertEquals(Status.NOT_FOUND.getCode(), statusEx.getStatus().getCode());
        assertEquals("Table with id " + tableId + " not found", statusEx.getStatus().getDescription());

        verify(tableQueryService, times(1)).getTable(tableId);
    }

    @Test
    void getTableDetails_ShouldReturnInternalStatus_WhenExceptionCaptured() {
        String tableId = "tableId";
        when(tableQueryService.getTable(tableId)).thenThrow(new RuntimeException(""));

        TableCheckRequest req = TableCheckRequest.newBuilder().setTableId(tableId).build();
        grpcTableServer.getTableDetails(req, detailsResponseObserver);

        ArgumentCaptor<Throwable> captor = ArgumentCaptor.forClass(Throwable.class);
        verify(detailsResponseObserver).onError(captor.capture());

        Throwable thrown = captor.getValue();
        assertInstanceOf(StatusRuntimeException.class, thrown);
        StatusRuntimeException statusEx = (StatusRuntimeException) thrown;
        assertEquals(Status.INTERNAL.getCode(), statusEx.getStatus().getCode());
        assertEquals("Internal server error", statusEx.getStatus().getDescription());

        verify(tableQueryService, times(1)).getTable(tableId);
    }
}