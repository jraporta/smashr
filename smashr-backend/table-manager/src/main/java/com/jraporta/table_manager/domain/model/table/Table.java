package com.jraporta.table_manager.domain.model.table;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Table {

    private String id;

    private String name;

    private String description;

}
