package com.jraporta.game.manager.adapter.in.web;

import com.jraporta.game.manager.adapter.out.grpc.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class GrpcExampleController {

    private final TableService tableService;

    @PostMapping("sayhello/{name}")
    public ResponseEntity<String> sayHello(@PathVariable String name) {
        return ResponseEntity.ok(tableService.pushSayHello(name));
    }

}
