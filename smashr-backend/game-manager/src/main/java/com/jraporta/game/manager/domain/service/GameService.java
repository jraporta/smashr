package com.jraporta.game.manager.domain.service;

import com.jraporta.game.manager.domain.model.game.Game;
import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class GameService {

    private final TableCheckerPort tableCheckerPort;

    public Game createGame(String playerId, String tableId, LocalDateTime startDateTime, Duration duration) {
        if (!tableCheckerPort.tableExists(tableId)) {
            throw new IllegalArgumentException("Table does not exist");
        }
        return Game.create(playerId, tableId, startDateTime, duration);
    }
}
