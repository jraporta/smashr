package com.jraporta.table_manager.applicaton.usecase;

import com.jraporta.table_manager.domain.model.table.Table;
import com.jraporta.table_manager.domain.port.out.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TableService implements TableUseCase {

    private final TableRepository tableRepository;

    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public Table addTable(String name, String description) {
        return tableRepository.addTable(name, description);
    }

    @Override
    public Table getTable(String id) {
        return tableRepository.findTable(id);
    }
}
