package com.jraporta.table_manager.applicaton.usecase;

import com.jraporta.table_manager.domain.model.table.Table;
import com.jraporta.table_manager.domain.port.out.TableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    @ParameterizedTest
    @MethodSource("provideTableLists")
    @DisplayName("getAllTables should handle different table list scenarios")
    void getAllTables_shouldHandleVariousScenarios(List<Table> tables) {
        Mockito.when(tableRepository.findAll()).thenReturn(tables);

        List<Table> response = tableService.getAllTables();

        assertEquals(tables, response);
    }

    static Stream<List<Table>> provideTableLists() {
        return Stream.of(
                List.of(new Table("id1", "name1")),
                List.of(new Table("id1", "name1"), new Table("id2", "name2")),
                List.of(),
                null
        );
    }
}