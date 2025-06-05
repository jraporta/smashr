package com.jraporta.table_manager.adapter.in.web;

import com.jraporta.table_manager.adapter.in.web.dto.request.AddTableRequest;
import com.jraporta.table_manager.applicaton.usecase.TableUseCase;
import com.jraporta.table_manager.domain.model.table.Table;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class TableController {

    private final TableUseCase tableUseCase;

    @GetMapping("/tables")
    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableUseCase.getAllTables());
    }

    @PostMapping("tables")
    public ResponseEntity<Table> addTable(@RequestBody AddTableRequest req) {
        return ResponseEntity.ok(tableUseCase.addTable(req.getName(), req.getDescription()));
    }
}
