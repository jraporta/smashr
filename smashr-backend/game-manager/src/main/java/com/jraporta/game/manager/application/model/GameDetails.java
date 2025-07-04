package com.jraporta.game.manager.application.model;

import com.jraporta.game.manager.domain.model.game.*;
import lombok.Getter;

@Getter
public class GameDetails {

    private final String id;
    private final TableDetails table;
    private final GamePlayers players;
    private final GameSchedule schedule;
    private final GameStatus status;
    private final GameResult result;

    public GameDetails(Game game, TableDetails tableDetails) {
        this.id = game.getId();
        this.table = tableDetails;
        this.players = game.getPlayers();
        this.schedule = game.getSchedule();
        this.status = game.getStatus();
        this.result = game.getResult();
    }
}
