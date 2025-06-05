package com.jraporta.table_manager.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jraporta.table_manager.adapter.in.web.dto.request.AddTableRequest;
import com.jraporta.table_manager.applicaton.usecase.TableUseCase;
import com.jraporta.table_manager.domain.exception.TableNotFoundException;
import com.jraporta.table_manager.domain.model.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableControllerTest {

    @Mock
    private TableUseCase tableUseCase;

    @InjectMocks
    private TableController tableController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideTableLists")
    @DisplayName("getAllTables should handle different table list scenarios")
    void getAllTables_whenExecuted_shouldHandleVariousScenarios(List<Table> tables) throws Exception {
        when(tableUseCase.getAllTables()).thenReturn(tables);

        mockMvc.perform(get("/tables"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tables), JsonCompareMode.STRICT));

        verify(tableUseCase, times(1)).getAllTables();
    }

    static Stream<List<Table>> provideTableLists() {
        return Stream.of(
                List.of(new Table("id1", "name1", "description1")),
                List.of(new Table("id1", "name1", "description1"), new Table("id2", "name2", "description2")),
                List.of()
        );
    }

    @Test
    void addTable_whenDataProvided_shouldReturnSavedTable() throws Exception {
        String name = "name";
        String description = "description";
        Table savedTable = new Table(null, name, description);
        AddTableRequest req = new AddTableRequest();
        req.setName(name);
        req.setDescription(description);

        when(tableUseCase.addTable(name, description)).thenReturn(savedTable);

        mockMvc.perform(post("/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(savedTable), JsonCompareMode.STRICT));

        verify(tableUseCase, times(1)).addTable(name, description);
    }

    @Test
    void addTable_WhenValidIdProvided_ShouldReturnTable() throws Exception {
        String id = "validId";
        Table table = new Table(id, "name", "description");
        when(tableUseCase.getTable(id)).thenReturn(table);

        mockMvc.perform(get("/tables/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(table), JsonCompareMode.STRICT));

        verify(tableUseCase, times(1)).getTable(id);
    }

    @Test
    void addTable_WhenNotValidIdProvided_ShouldReturnNotFoundResponse() throws Exception {
        String id = "nonExistingId";
        when(tableUseCase.getTable(id)).thenThrow(new TableNotFoundException("someErrorMessage"));

        mockMvc.perform(get("/tables/{id}", id))
                .andExpect(status().isNotFound());

        verify(tableUseCase, times(1)).getTable(id);
    }
}