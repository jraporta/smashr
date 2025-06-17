package com.jraporta.game.manager.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jraporta.game.manager.adapter.in.web.dto.request.CreateGameRequest;
import com.jraporta.game.manager.application.usecase.GameUseCase;
import com.jraporta.game.manager.domain.model.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameUseCase gameUseCase;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideGameLists")
    void getAllGames_ShouldHandleVariousScenarios(List<Game> games) throws Exception {
        when(gameUseCase.getAllGames()).thenReturn(games);

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(games), JsonCompareMode.STRICT));

        verify(gameUseCase, times(1)).getAllGames();
    }

    public static Stream<List<Game>> provideGameLists() {
        Game game1 = Game.create("player1", "table1", LocalDateTime.now(), Duration.ofMinutes(60));
        Game game2 = Game.create("player2", "table2", LocalDateTime.now(), Duration.ofMinutes(60));
        return Stream.of(
                List.of(game1),
                List.of(game1, game2),
                List.of()
        );
    }

    @Test
    void getGame_WhenValidId_ShouldReturnGame() throws Exception {
        String id = "ValidId";
        Game game1 = Game.create("player1", "table1", LocalDateTime.now(), Duration.ofMinutes(60));

        when(gameUseCase.getGame(id)).thenReturn(game1);

        mockMvc.perform(get(new UriTemplate("/games/{id}").expand(id)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(game1), JsonCompareMode.STRICT));

        verify(gameUseCase, times(1)).getGame(id);
    }

    @Test
    void getGame_WhenNoGameWithGivenId_ShouldReturn404Response() throws Exception {
        String id = "NonExistingGame";

        when(gameUseCase.getGame(id)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(new UriTemplate("/games/{id}").expand(id)))
                .andExpect(status().isNotFound());

        verify(gameUseCase, times(1)).getGame(id);
    }

    @Test
    void createGame_WhenValidDataProvided_ShouldReturnCreatedGame() throws Exception {
        String player = "player1";
        String table = "table1";
        LocalDateTime startDateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(60);
        CreateGameRequest req = new CreateGameRequest(player, table, startDateTime, duration);

        Game createdGame = Game.create(player, table, startDateTime, duration);
        when(gameUseCase.createGame(player, table, startDateTime, duration)).thenReturn(createdGame);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(createdGame), JsonCompareMode.STRICT));

        verify(gameUseCase, times(1)).createGame(player, table, startDateTime, duration);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void createGame_WhenInvalidData_ShouldReturnBadRequestResponse(CreateGameRequest req, List<String> expectedErrors) throws Exception {
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    for (String s : expectedErrors) {
                        assertTrue(content.contains(s), "Expected content to contain: " + s);
                    }
                });
    }

    public static Stream<Arguments> provideInvalidData() {
        String player = "player1";
        String table = "table1";
        LocalDateTime startDateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(60);
        return Stream.of(
                Arguments.of(new CreateGameRequest(null, table, startDateTime, duration), List.of("Player is required")),
                Arguments.of(new CreateGameRequest(player, null, startDateTime, duration), List.of("Table is required")),
                Arguments.of(new CreateGameRequest(player, table, null, duration), List.of("Game start date and time is required")),
                Arguments.of(new CreateGameRequest(player, table, startDateTime, null), List.of("Game duration is required")),
                Arguments.of(new CreateGameRequest("", "   ", startDateTime, duration), List.of("Player is required", "Table is required")),
                Arguments.of(new CreateGameRequest("", "   ", null, null), List.of("Player is required", "Table is required", "Game start date and time is required", "Game duration is required"))
                );
    }
}