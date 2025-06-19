package com.jraporta.game.manager.adapter.out.mongo;

import com.jraporta.game.manager.domain.model.game.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class GameDocument {
    @Id
    private String id;
    private String table;
    private List<String> players;
    private GameType type;
    private GameSchedule schedule;
    private GameStatus status;
    private GameResult result;

    public GameDocument(Game game) {
        this.id = game.getId();
        this.table = game.getTable();
        this.players = game.getPlayers().getPlayers().stream().toList();
        this.type = game.getPlayers().getType();
        this.schedule = game.getSchedule();
        this.status = game.getStatus();
        this.result = game.getResult();
    }

    public Game toDomain() {
        return Game.builder()
                .id(this.id)
                .table(this.table)
                .players(buildGamePlayers())
                .schedule(this.schedule)
                .status(this.status)
                .result(this.result)
                .build();
    }

    private GamePlayers buildGamePlayers() {
        if (this.players == null || this.players.isEmpty()) {
            throw new IllegalStateException("Cannot create GamePlayers with no players");
        }
        Iterator<String> playerIterator = this.players.iterator();
        GamePlayers gamePlayers = new GamePlayers(playerIterator.next(), this.type);
        while (playerIterator.hasNext()) {
            gamePlayers.addPlayer(playerIterator.next());
        }
        return gamePlayers;
    }
}
