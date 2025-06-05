package com.jraporta.table_manager.adapter.in.web;

import com.jraporta.table_manager.domain.exception.TableNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TableNotFoundException.class)
    ResponseEntity<String> handleTableNotFoundException(TableNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
