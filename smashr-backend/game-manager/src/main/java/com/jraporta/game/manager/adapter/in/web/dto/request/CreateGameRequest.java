package com.jraporta.game.manager.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class CreateGameRequest {

    @NotNull(message = "Player is required")
    private String playerId;
    @NotNull(message = "Table is required")
    private String tableId;
    @NotNull(message = "Game start date and time is required")
    private LocalDateTime startDateTime;
    @NotNull(message = "Game duration is required")
    private Duration duration;

}
