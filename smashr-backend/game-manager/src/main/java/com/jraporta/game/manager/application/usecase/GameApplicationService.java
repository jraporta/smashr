package com.jraporta.game.manager.application.usecase;

import com.jraporta.game.manager.domain.model.game.Game;
import com.jraporta.game.manager.domain.port.out.GameRepository;
import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import com.jraporta.game.manager.domain.port.out.UserCheckerPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GameApplicationService implements GameUseCase{

    private final GameRepository gameRepository;
    private final TableCheckerPort tableCheckerPort;
    private final UserCheckerPort userCheckerPort;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game createGame(String playerId, String tableId, LocalDateTime startDateTime, Duration duration) {
        if (!tableCheckerPort.tableExists(tableId)) {
            throw new IllegalArgumentException("Table does not exist");
        }
        if (!userCheckerPort.userExists(playerId)) {
            throw new IllegalArgumentException("User does not exist");
        }
        Game game = Game.create(playerId, tableId, startDateTime, duration);
        return gameRepository.saveGame(game);
    }

    @Override
    public Game getGame(String id) {
        return gameRepository.findGame(id);
    }
}
