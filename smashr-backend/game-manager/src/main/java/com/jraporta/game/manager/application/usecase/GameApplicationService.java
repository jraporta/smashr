package com.jraporta.game.manager.application.usecase;

import com.jraporta.game.manager.application.exception.GameDetailsLoadException;
import com.jraporta.game.manager.application.exception.GameNotFoundException;
import com.jraporta.game.manager.application.exception.QueryServiceInternalException;
import com.jraporta.game.manager.application.exception.TableNotFoundException;
import com.jraporta.game.manager.application.model.GameDetails;
import com.jraporta.game.manager.application.port.out.TableQueryService;
import com.jraporta.game.manager.application.model.TableDetails;
import com.jraporta.game.manager.domain.model.game.Game;
import com.jraporta.game.manager.domain.port.out.GameRepository;
import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import com.jraporta.game.manager.domain.port.out.UserCheckerPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GameApplicationService implements GameUseCase{

    private final GameRepository gameRepository;
    private final TableCheckerPort tableCheckerPort;
    private final UserCheckerPort userCheckerPort;
    private final TableQueryService tableQueryService;

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
    public GameDetails getGame(String gameId) {
        Game game = gameRepository.findGame(gameId)
                .orElseThrow(() -> {
                    log.warn("Game with id {} not found", gameId);
                    return new GameNotFoundException("Game with id " + gameId + " not found");
                });

        TableDetails tableDetails;
        try {
            tableDetails = tableQueryService.getTableDetails(game.getTable());
        } catch (TableNotFoundException | QueryServiceInternalException ex) {
            log.error("Failed to load table details for game {}: {}", gameId, ex.getMessage(), ex);
            throw new GameDetailsLoadException("Unable to load table details for game " + gameId);
        }
        return new GameDetails(game, tableDetails);
    }

    @Override
    public List<Game> getGamesFiltered(String tableId, LocalDateTime from, LocalDateTime to) {
        return gameRepository.getGamesFiltered(tableId, from, to);
    }
}
