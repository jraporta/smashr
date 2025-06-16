package com.jraporta.game.manager.domain.model.game;

import lombok.Getter;

@Getter
public enum GameType {
    SINGLES(2),
    DOUBLES(4);

    private final int numberOfPlayers;

    GameType(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

}
