package com.jraporta.game.manager.adapter.out.mongo;

import com.jraporta.game.manager.domain.model.game.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class GameDocument {
    @Id
    private String id;
    private String table;
    private GamePlayers players;
    private GameSchedule schedule;
    private GameStatus status;
    private GameResult result;

    public GameDocument(Game game) {
        this.id = game.getId();
        this.table = game.getTable();
        this.players = game.getPlayers();
        this.schedule = game.getSchedule();
        this.status = game.getStatus();
        this.result = game.getResult();
    }

    public Game toDomain() {
        return Game.builder()
                .id(this.id)
                .table(this.table)
                .players(this.players)
                .schedule(this.schedule)
                .status(this.status)
                .result(this.result)
                .build();
    }
}
