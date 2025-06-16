package com.jraporta.game.manager.domain.model.game;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class GameSchedule {

    private final LocalDateTime startDateTime;
    private final Duration duration;

    public GameSchedule(LocalDateTime startDateTime, Duration duration) {
        this.startDateTime = startDateTime;
        this.duration = duration;
    }
}
