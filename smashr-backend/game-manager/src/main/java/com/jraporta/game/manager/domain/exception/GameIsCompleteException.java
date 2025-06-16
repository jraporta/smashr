package com.jraporta.game.manager.domain.exception;

public class GameIsCompleteException extends RuntimeException {
    public GameIsCompleteException(String message) {
        super(message);
    }
}
