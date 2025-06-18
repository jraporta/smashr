package com.jraporta.table_manager.integration;

import com.jraporta.table_manager.domain.port.out.TableRepository;
import com.jraporta.technical.api.proto.TableCheckReply;
import com.jraporta.technical.api.proto.TableCheckRequest;
import com.jraporta.technical.api.proto.TableServiceGrpc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(StubTestConfig.class)
class GrpcTableServerIntegrationTest {

    @Autowired
    private TableServiceGrpc.TableServiceBlockingStub tableServiceBlockingStub;

    @MockitoBean
    private TableRepository tableRepository;

    @Test
    void shouldReturnExistsTrue_WhenTableExists() {
        String tableId = "existingTable";

        when(tableRepository.tableExists(tableId)).thenReturn(true);

        TableCheckRequest request = TableCheckRequest.newBuilder()
                .setTableId(tableId)
                .build();

        TableCheckReply reply = tableServiceBlockingStub.checkTableExists(request);

        assertTrue(reply.getExists());
    }

    @Test
    void shouldReturnExistsFalseWhenTableDoesNotExist() {
        String tableId = "nonExistingTable";

        when(tableRepository.tableExists(tableId)).thenReturn(false);

        TableCheckRequest request = TableCheckRequest.newBuilder()
                .setTableId(tableId)
                .build();

        TableCheckReply reply = tableServiceBlockingStub.checkTableExists(request);

        assertFalse(reply.getExists());
    }
}