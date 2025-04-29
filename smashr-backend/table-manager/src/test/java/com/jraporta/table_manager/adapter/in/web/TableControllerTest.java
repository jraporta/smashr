package com.jraporta.table_manager.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jraporta.table_manager.applicaton.usecase.TableUseCase;
import com.jraporta.table_manager.domain.model.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
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
                List.of(new Table("id1", "name1")),
                List.of(new Table("id1", "name1"), new Table("id2", "name2")),
                List.of()
        );
    }

}