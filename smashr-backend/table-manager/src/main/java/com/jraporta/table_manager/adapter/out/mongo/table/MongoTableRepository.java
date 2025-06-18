package com.jraporta.table_manager.adapter.out.mongo.table;

import com.jraporta.table_manager.domain.exception.TableNotFoundException;
import com.jraporta.table_manager.domain.model.table.Table;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class MongoTableRepository implements com.jraporta.table_manager.domain.port.out.TableRepository {

    private final SpringMongoTableRepository springMongoTableRepository;


    @Override
    public List<Table> findAll() {
        return springMongoTableRepository.findAll().stream().map(TableDocument::toDomain).toList();
    }

    @Override
    public Table addTable(String name, String description) {
        return springMongoTableRepository.save(new TableDocument(null, name, description)).toDomain();
    }

    @Override
    public Table findTable(String id) {
        return springMongoTableRepository
                .findById(id)
                .orElseThrow(() -> new TableNotFoundException(String.format("No table found with id: %s", id)))
                .toDomain();
    }

    @Override
    public boolean tableExists(String id) {
        return springMongoTableRepository.existsById(id);
    }
}
