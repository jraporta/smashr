package com.jraporta.game.manager.application.model;

import com.jraporta.game.manager.domain.model.game.*;

public record GameDetails(
        String id,
        TableDetails table,
        GamePlayers players,
        GameSchedule schedule,
        GameStatus status,
        GameResult result
) {
    public GameDetails(Game game, TableDetails tableDetails) {
        this(
                game.getId(),
                tableDetails,
                game.getPlayers(),
                game.getSchedule(),
                game.getStatus(),
                game.getResult()
        );
    }
}
