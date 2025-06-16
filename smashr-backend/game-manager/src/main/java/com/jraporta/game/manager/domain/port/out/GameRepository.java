package com.jraporta.game.manager.domain.port.out;

import com.jraporta.game.manager.domain.model.game.Game;

import java.util.List;

public interface GameRepository {

    List<Game> findAll();
    Game saveGame(Game game);
    Game findGame(String id);

}
