package com.jraporta.game.manager.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateGameRequest {

    @NotBlank(message = "Player is required")
    private String playerId;
    @NotBlank(message = "Table is required")
    private String tableId;
    @NotNull(message = "Game start date and time is required")
    private LocalDateTime startDateTime;
    @NotNull(message = "Game duration is required")
    private Duration duration;

}
