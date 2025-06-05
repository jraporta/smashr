package com.jraporta.table_manager.adapter.out.mongo.table;

import com.jraporta.table_manager.domain.model.table.Table;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document(collection = "tables")
public class TableDocument {

    @Id
    private String id;

    private String name;

    private String description;

    public Table toDomain() {
        return new Table(id, name, description);
    }

}
