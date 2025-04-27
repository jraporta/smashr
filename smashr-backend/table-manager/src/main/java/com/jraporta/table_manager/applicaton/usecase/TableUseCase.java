package com.jraporta.table_manager.applicaton.usecase;

import com.jraporta.table_manager.domain.model.table.Table;

import java.util.List;

public interface TableUseCase {

    List<Table> getAllTables();

}
