package com.jraporta.table_manager.domain.port.out;

import com.jraporta.table_manager.domain.model.table.Table;

import java.util.List;

public interface TableRepository {

    List<Table> findAll();

}
