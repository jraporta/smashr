package com.jraporta.game.manager.application.usecase;

import com.jraporta.game.manager.domain.model.game.Game;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface GameUseCase {

    List<Game> getAllGames();
    Game createGame(String playerId, String table, LocalDateTime startDateTime, Duration duration);
    Game getGame(String id);

}
