package com.jraporta.game.manager.domain.model.game;

import com.jraporta.game.manager.domain.exception.GameIsCompleteException;
import com.jraporta.game.manager.domain.exception.UserAlreadyInGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class GameTest {

    private LocalDateTime now;
    private Duration duration;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        duration = Duration.ofHours(1);
    }

    @Test
    void create_ShouldInitializeCorrectly() {
        String playerId = "player1";
        String table = "table1";

        Game game = Game.create(playerId, table, now, duration);

        assertThat(game.getPlayers().getPlayers()).containsExactly(playerId);
        assertThat(game.getTable()).isEqualTo(table);
        assertThat(game.getSchedule().getStartDateTime()).isEqualTo(now);
        assertThat(game.getSchedule().getDuration()).isEqualTo(duration);
        assertThat(game.getPlayers().getType()).isEqualTo(GameType.SINGLES);
        assertThat(game.getStatus()).isEqualTo(GameStatus.WAITING_FOR_PLAYERS);
        assertThat(game.getResult()).isNull();
    }

    @ParameterizedTest
    @MethodSource("getSources")
    void create_ShouldThrowException_WhenPlayerOrTableIdIsNullOrBlank(String playerId, String tableId, String message) {
        assertThatThrownBy(() -> Game.create(playerId, tableId, now, duration))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message);
    }

    public static Stream<Arguments> getSources() {
        String message1 = "PlayerId cannot be null or empty";
        String message2 = "TableId cannot be null or empty";
        String player1 = "player1";
        String table1 = "table1";
        return Stream.of(
                Arguments.of(null, table1, message1),
                Arguments.of(" ", table1, message1),
                Arguments.of(player1, null, message2),
                Arguments.of(player1, " ", message2)
        );
    }

    @Test
    void joinGame_ShouldAllowNewPlayerAndActivateGame_WhenFull() {
        String player1 = "player1";
        String player2 = "player2";

        Game game = Game.create(player1, "table1", now, duration);

        game.joinGame(player2);

        assertThat(game.getPlayers().getPlayers()).containsExactlyInAnyOrder(player1, player2);
        assertThat(game.getStatus()).isEqualTo(GameStatus.ACTIVE);
    }

    @Test
    void joinGame_ShouldThrowException_WhenGameIsAlreadyFull() {
        Game game = Game.create("player1", "table1", now, duration);
        game.joinGame("player2");

        assertThatThrownBy(() -> game.joinGame("player3"))
                .isInstanceOf(GameIsCompleteException.class)
                .hasMessageMatching("Cannot add player: game already has .*/.* players");
    }

    @Test
    void joinGame_ShouldThrow_WhenUserAlreadyInGame() {
        String player1 = "player1";
        Game game = Game.create(player1, "table1", now, duration);

        assertThatThrownBy(() -> game.joinGame(player1))
                .isInstanceOf(UserAlreadyInGameException.class)
                .hasMessageContaining("Player with id " + player1 + " is already in the game");
    }

    @Test
    void joinGame_GameStatusShouldChangeToActive_WhenGameIsFull() {
        Game game = Game.create("player1", "table1", now, duration);

        assertThat(game.getPlayers().isComplete()).isFalse();
        assertThat(game.getStatus()).isEqualTo(GameStatus.WAITING_FOR_PLAYERS);

        game.joinGame("player2");

        assertThat(game.getPlayers().isComplete()).isTrue();
        assertThat(game.getStatus()).isEqualTo(GameStatus.ACTIVE);
    }
}