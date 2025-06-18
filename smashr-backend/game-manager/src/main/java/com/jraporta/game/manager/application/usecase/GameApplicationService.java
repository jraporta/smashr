package com.jraporta.game.manager.application.usecase;

import com.jraporta.game.manager.domain.model.game.Game;
import com.jraporta.game.manager.domain.port.out.GameRepository;
import com.jraporta.game.manager.domain.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GameApplicationService implements GameUseCase{

    private final GameRepository gameRepository;
    private final GameService gameService;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game createGame(String playerId, String table, LocalDateTime startDateTime, Duration duration) {
        Game game = gameService.createGame(playerId, table, startDateTime, duration);
        return gameRepository.saveGame(game);
    }

    @Override
    public Game getGame(String id) {
        return gameRepository.findGame(id);
    }
}
