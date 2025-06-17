package com.jraporta.game.manager.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class Game {

    private String id;
    private String table;
    private GamePlayers players;
    private GameSchedule schedule;
    @Builder.Default
    private GameStatus status = GameStatus.WAITING_FOR_PLAYERS;
    private GameResult result = null;

    public static Game create(String playerId, String tableId, LocalDateTime startDateTime, Duration duration) {
        checkTable(tableId);
        return Game.builder()
                .table(tableId)
                .players(new GamePlayers(playerId, GameType.SINGLES))
                .schedule(new GameSchedule(startDateTime, duration))
                .build();
    }

    public void joinGame(String playerId) {
        players.addPlayer(playerId);
        if (players.isComplete()) {
            status = GameStatus.ACTIVE;
        }
    }

    private static void checkTable(String tableId) {
        if (tableId == null || tableId.isBlank()) {
            throw new IllegalArgumentException("TableId cannot be null or empty");
        }
    }

}
