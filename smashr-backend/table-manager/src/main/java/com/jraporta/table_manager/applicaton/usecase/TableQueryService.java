package com.jraporta.table_manager.applicaton.usecase;

import com.jraporta.table_manager.domain.model.table.Table;

public interface TableQueryService {

    boolean tableExists(String tableId);

    Table getTable(String tableId);
}
