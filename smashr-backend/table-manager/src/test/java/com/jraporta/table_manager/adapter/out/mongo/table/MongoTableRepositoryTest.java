package com.jraporta.table_manager.adapter.out.mongo.table;

import com.jraporta.table_manager.domain.model.table.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MongoTableRepositoryTest {

    @Mock
    private SpringMongoTableRepository springMongoTableRepository;

    @InjectMocks
    private MongoTableRepository mongoTableRepository;

    @Test
    @DisplayName("findAll should map TableDocuments to Tables correctly")
    void findAll_ShouldMapTableDocumentsToTables() {
        TableDocument doc1 = new TableDocument("id1", "name1", "description1");
        TableDocument doc2 = new TableDocument("id2", "name2", "description2");

        when(springMongoTableRepository.findAll()).thenReturn(List.of(doc1, doc2));

        List<Table> result = mongoTableRepository.findAll();

        assertEquals(2, result.size());
        assertEquals(doc1.toDomain(), result.getFirst());
        assertEquals(doc2.toDomain(), result.getLast());

        verify(springMongoTableRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll should return empty list when no documents are found")
    void findAll_ShouldReturnEmptyList_WhenNoDocumentsFound() {
        when(springMongoTableRepository.findAll()).thenReturn(List.of());

        List<Table> result = mongoTableRepository.findAll();

        assertTrue(result.isEmpty());

        verify(springMongoTableRepository, times(1)).findAll();
    }

    @Test
    void addTable_ShouldAddTable_WhenDataProvided() {
        String name = "name";
        String description = "description";
        TableDocument entity = new TableDocument(null, name, description);

        when((springMongoTableRepository.save(any()))).thenReturn(entity);

        assertEquals(entity.toDomain(), mongoTableRepository.addTable(name, description));

        verify(springMongoTableRepository, times(1)).save(any());
    }
}