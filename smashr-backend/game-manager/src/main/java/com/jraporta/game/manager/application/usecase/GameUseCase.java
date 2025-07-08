package com.jraporta.game.manager.application.usecase;

import com.jraporta.game.manager.application.exception.GameNotFoundException;
import com.jraporta.game.manager.application.exception.GameDetailsLoadException;
import com.jraporta.game.manager.application.model.GameDetails;
import com.jraporta.game.manager.domain.model.game.Game;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface GameUseCase {

    List<Game> getAllGames();
    Game createGame(String playerId, String tableId, LocalDateTime startDateTime, Duration duration);
    /**
     * @param gameId the gameId
     * @return the details of the game
     * @throws  GameNotFoundException if game not found
     * @throws GameDetailsLoadException if problem getting game details
     */
    GameDetails getGame(String gameId);
    List<Game> getGamesFiltered(String tableId, LocalDateTime from, LocalDateTime to);
}
