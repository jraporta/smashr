package com.jraporta.table_manager.applicaton.usecase;

import com.jraporta.table_manager.domain.port.out.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TableQueryServiceImp implements TableQueryService{

    private final TableRepository tableRepository;

    @Override
    public boolean tableExists(String tableId) {
        return tableRepository.tableExists(tableId);
    }

}
