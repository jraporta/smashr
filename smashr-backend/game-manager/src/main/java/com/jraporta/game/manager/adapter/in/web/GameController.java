package com.jraporta.game.manager.adapter.in.web;

import com.jraporta.game.manager.adapter.in.web.dto.request.CreateGameRequest;
import com.jraporta.game.manager.application.usecase.GameUseCase;
import com.jraporta.game.manager.domain.model.game.Game;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
public class GameController {

    private final GameUseCase gameUseCase;

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames(
            @RequestParam(required = false) String tableId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(
                tableId == null && from == null && to == null ?
                        gameUseCase.getAllGames():
                        gameUseCase.getGamesFiltered(tableId, from, to));
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGame(@PathVariable String id) {
        return ResponseEntity.ok(gameUseCase.getGame(id));
    }

    @PostMapping("games")
    public ResponseEntity<Game> createGame(@Valid @RequestBody CreateGameRequest req) {
        return ResponseEntity.ok(gameUseCase
                .createGame(req.getPlayerId(),
                        req.getTableId(),
                        req.getStartDateTime(),
                        req.getDuration()
                ));
    }

}
