package com.jraporta.game.manager.domain.model.game;

import com.jraporta.game.manager.domain.exception.GameIsCompleteException;
import com.jraporta.game.manager.domain.exception.UserAlreadyInGameException;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class Game {

    private String id;
    private String table;
    @Builder.Default
    private Set<String> players = new HashSet<>();
    private GameSchedule schedule;
    private GameType type;
    @Builder.Default
    private GameStatus status = GameStatus.PENDING;
    private GameResult result = null;

    public static Game createGame(String playerId, String table, LocalDateTime startDateTime, Duration duration) {
        Game game = Game.builder()
                .table(table)
                .schedule(new GameSchedule(startDateTime, duration))
                .type(GameType.SINGLES)
                .build();
        game.players.add(playerId);
        return game;
    }

    public void joinGame(String playerId) {
        if (this.players.size() >= this.type.getNumberOfPlayers()) {
            throw new GameIsCompleteException("The game is complete, no more players can join the game");
        }
        if (!this.players.add(playerId)) {
            throw new UserAlreadyInGameException(String.format("User with id {%s} is already in the game", playerId));
        }
        if (this.players.size() == this.type.getNumberOfPlayers()) {
            this.status = GameStatus.ACTIVE;
        }
    }

}
