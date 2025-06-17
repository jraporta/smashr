package com.jraporta.game.manager.domain.model.game;

import com.jraporta.game.manager.domain.exception.GameIsCompleteException;
import com.jraporta.game.manager.domain.exception.UserAlreadyInGameException;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GamePlayers {

    private final Set<String> players;
    @Getter
    private final GameType type;

    public GamePlayers(String player, GameType type) {
        checkPlayer(player);
        this.players = new HashSet<>();
        players.add(player);
        this.type = type;
    }

    public Set<String> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public void addPlayer(String player) {
        checkPlayer(player);
        if (players.size() >= type.getNumberOfPlayers()) {
            throw new GameIsCompleteException(String.format(
                    "Cannot add player: game already has %d/%d players",
                    players.size(), type.getNumberOfPlayers()));
        }
        if (!players.add(player)) {
            throw new UserAlreadyInGameException(String.format("Player with id %s is already in the game", player));
        }
    }

    public boolean isComplete() {
        return players.size() >= type.getNumberOfPlayers();
    }

    private void checkPlayer(String player) {
        if (player == null || player.isBlank()) {
            throw new IllegalArgumentException("PlayerId cannot be null or empty");
        }
    }
}
