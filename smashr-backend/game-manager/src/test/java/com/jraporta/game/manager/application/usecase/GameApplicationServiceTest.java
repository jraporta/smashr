package com.jraporta.game.manager.application.usecase;

import com.jraporta.game.manager.domain.model.game.Game;
import com.jraporta.game.manager.domain.port.out.GameRepository;
import com.jraporta.game.manager.domain.port.out.TableCheckerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameApplicationServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private TableCheckerPort tableCheckerPort;

    @InjectMocks
    private GameApplicationService gameApplicationService;

    @ParameterizedTest
    @MethodSource("provideGameLists")
    void getAllGames_ShouldHandleVariousScenarios(List<Game> games) {
        Mockito.when(gameRepository.findAll()).thenReturn(games);

        List<Game> response = gameApplicationService.getAllGames();

        assertEquals(games, response);
    }

    public static Stream<List<Game>> provideGameLists() {
        Game game1 = Game.create("player1", "table1", LocalDateTime.now(), Duration.ofMinutes(60));
        Game game2 = Game.create("player2", "table2", LocalDateTime.now(), Duration.ofMinutes(60));
        return Stream.of(
                List.of(game1),
                List.of(game1, game2),
                List.of(),
                null
        );
    }

    @Test
    void createGame_ShouldCreateGame_WhenTableExists() {
        String player = "player1";
        String table = "existingTable";
        LocalDateTime startDateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(60);

        Mockito.when(gameRepository.saveGame(any(Game.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(tableCheckerPort.tableExists(table))
                .thenReturn(true);

        Game response = gameApplicationService.createGame(player, table, startDateTime, duration);

        assertNotNull(response);
        assertTrue(response.getPlayers().getPlayers().contains(player));
        assertEquals(table, response.getTable());
        assertEquals(startDateTime, response.getSchedule().getStartDateTime());
        assertEquals(duration, response.getSchedule().getDuration());

        verify(gameRepository, times(1)).saveGame(any());
        verify(tableCheckerPort, times(1)).tableExists(table);
    }

    @Test
    void createGame_ShouldNotCreateGame_WhenTableNotExists() {
        String player = "player1";
        String table = "nonExistingTable";
        LocalDateTime startDateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(60);

        Mockito.when(tableCheckerPort.tableExists(table))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> gameApplicationService.createGame(player, table, startDateTime, duration));

        verify(tableCheckerPort, times(1)).tableExists(table);
    }

    @Test
    void getGame_IfGameIdIsValid_ShouldReturnGame() {
        String gameId = "game1";
        Game expectedResponse = Game.create("player1", "table1", LocalDateTime.now(), Duration.ofMinutes(60));

        Mockito.when(gameRepository.findGame(gameId)).thenReturn(expectedResponse);

        assertEquals(expectedResponse, gameApplicationService.getGame(gameId));

        verify(gameRepository, times(1)).findGame(gameId);
    }
}