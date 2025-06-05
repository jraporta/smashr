package com.jraporta.table_manager.applicaton.usecase;

import com.jraporta.table_manager.domain.model.table.Table;
import com.jraporta.table_manager.domain.port.out.TableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
                List.of(new Table("id1", "name1", "description1")),
                List.of(new Table("id1", "name1", "description1"), new Table("id2", "name2", "description2")),
                List.of(),
                null
        );
    }

    @Test
    void addTable_shouldAddTable() {
        String name = "name";
        String description = "description";
        Table savedTable = new Table(null, name, description);

        Mockito.when(tableRepository.addTable(name, description)).thenReturn(savedTable);

        Table response = tableService.addTable(name, description);

        assertEquals(savedTable, response);

        verify(tableRepository, times(1)).addTable(name, description);
    }

    @Test
    void getTable_IfIdIsValid_ShouldReturnTable() {
        String id = "validId";
        Table expectedResponse = new Table(id, "name", "description");

        Mockito.when(tableRepository.findTable(id)).thenReturn(expectedResponse);

        assertEquals(expectedResponse, tableService.getTable(id));

        verify(tableRepository, times(1)).findTable(id);
    }
}