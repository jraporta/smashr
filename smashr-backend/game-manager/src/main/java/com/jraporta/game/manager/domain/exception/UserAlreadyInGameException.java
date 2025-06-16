package com.jraporta.game.manager.domain.exception;

public class UserAlreadyInGameException extends RuntimeException {
    public UserAlreadyInGameException(String message) {
        super(message);
    }
}
