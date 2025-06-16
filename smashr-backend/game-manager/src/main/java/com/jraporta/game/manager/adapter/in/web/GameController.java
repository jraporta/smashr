package com.jraporta.game.manager.adapter.in.web;

import com.jraporta.game.manager.adapter.in.web.dto.request.CreateGameRequest;
import com.jraporta.game.manager.application.usecase.GameUseCase;
import com.jraporta.game.manager.domain.model.game.Game;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class GameController {

    private final GameUseCase gameUseCase;

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(gameUseCase.getAllGames());
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
