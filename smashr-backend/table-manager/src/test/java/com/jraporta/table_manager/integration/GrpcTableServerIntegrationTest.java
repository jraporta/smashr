package com.jraporta.table_manager.integration;

import com.jraporta.table_manager.domain.exception.TableNotFoundException;
import com.jraporta.table_manager.domain.model.table.Table;
import com.jraporta.table_manager.domain.port.out.TableRepository;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableDetailsReply;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(StubTestConfig.class)
class GrpcTableServerIntegrationTest {

    @Autowired
    private TableServiceGrpc.TableServiceBlockingStub tableServiceBlockingStub;

    @MockitoBean
    private TableRepository tableRepository;

    @Test
    void checkTableExists_ShouldReturnExistsTrue_WhenTableExists() {
        String tableId = "existingTable";

        when(tableRepository.tableExists(tableId)).thenReturn(true);

        TableCheckRequest request = TableCheckRequest.newBuilder()
                .setTableId(tableId)
                .build();

        TableCheckReply reply = tableServiceBlockingStub.checkTableExists(request);

        assertTrue(reply.getExists());
    }

    @Test
    void checkTableExists_ShouldReturnExistsFalseWhenTableDoesNotExist() {
        String tableId = "nonExistingTable";

        when(tableRepository.tableExists(tableId)).thenReturn(false);

        TableCheckRequest request = TableCheckRequest.newBuilder()
                .setTableId(tableId)
                .build();

        TableCheckReply reply = tableServiceBlockingStub.checkTableExists(request);

        assertFalse(reply.getExists());
    }

    @Test
    void getTableDetails_ShouldReturnDetails_WhenTableFound() {
        String tableId = "existingTable";
        String name = "name";
        String description = "description";

        when(tableRepository.findTable(tableId)).thenReturn(new Table(tableId, name, description));

        TableCheckRequest request = TableCheckRequest.newBuilder()
                .setTableId(tableId)
                .build();

        TableDetailsReply reply = tableServiceBlockingStub.getTableDetails(request);

        assertAll("gRPC reply fields",
                () -> assertEquals(name, reply.getName()),
                () -> assertEquals(description, reply.getDescription())
        );

        verify(tableRepository, times(1)).findTable(tableId);
    }

    @Test
    void getTableDetails_ShouldReturnNotFoundStatus_WhenTableNotFound() {
        String tableId = "nonExistingTable";

        when(tableRepository.findTable(tableId)).thenThrow(new TableNotFoundException(""));

        TableCheckRequest request = TableCheckRequest.newBuilder()
                .setTableId(tableId)
                .build();

        StatusRuntimeException ex = assertThrows(StatusRuntimeException.class,
                () -> tableServiceBlockingStub.getTableDetails(request),
                "Expected NOT_FOUND error");
        assertAll("gRPC error details",
                () -> assertEquals(Status.NOT_FOUND.getCode(), ex.getStatus().getCode()),
                () -> assertEquals("Table with id " + tableId + " not found", ex.getStatus().getDescription())
        );

        verify(tableRepository, times(1)).findTable(tableId);
    }
}