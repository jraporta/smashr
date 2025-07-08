package com.jraporta.game.manager.domain.port.out;

import com.jraporta.game.manager.domain.model.game.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<Game> findAll();
    Game saveGame(Game game);
    Optional<Game> findGame(String id);
    List<Game> getGamesFiltered(String tableId, LocalDateTime from, LocalDateTime to);
}
