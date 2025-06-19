package com.jraporta.game.manager.adapter.out.mongo;

import com.jraporta.game.manager.domain.model.game.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoGameRepositoryTest {

    @Mock
    private SpringMongoGameRepository springMongoGameRepository;

    @InjectMocks
    private MongoGameRepository mongoGameRepository;

    private static GameDocument gameDoc1;
    private static String gameId1 = "gameId";

    @BeforeAll
    static void setup() {
        gameDoc1 = new GameDocument(
                gameId1,
                "tableId",
                List.of("playerId"),
                GameType.SINGLES,
                new GameSchedule(LocalDateTime.now(), Duration.ofMinutes(60)),
                GameStatus.WAITING_FOR_PLAYERS,
                null
        );
    }

    @Test
    void findAll_ShouldMapDocumentsToGames() {
        when(springMongoGameRepository.findAll()).thenReturn(List.of(gameDoc1));

        List<Game> result = mongoGameRepository.findAll();

        assertEquals(1, result.size());
        assertEquals(gameDoc1.toDomain(), result.getFirst());

        verify(springMongoGameRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoGamesFound() {
        when(springMongoGameRepository.findAll()).thenReturn(List.of());

        List<Game> result = mongoGameRepository.findAll();

        assertTrue(result.isEmpty());

        verify(springMongoGameRepository, times(1)).findAll();
    }

    @Test
    void saveGame_ShouldSaveGame() {
        when(springMongoGameRepository.save(any())).thenReturn(gameDoc1);

        assertEquals(gameDoc1.toDomain(), mongoGameRepository.saveGame(gameDoc1.toDomain()));

        verify(springMongoGameRepository, times(1)).save(any());
    }

    @Test
    void findGame_IfExists_ShouldReturnGame() {
        when(springMongoGameRepository.findById(gameId1)).thenReturn(Optional.of(gameDoc1));

        assertEquals(gameDoc1.toDomain(), mongoGameRepository.findGame(gameId1));

        verify(springMongoGameRepository, times(1)).findById(gameId1);
    }

    @Test
    void findGame_IfNotExists_ShouldThrowException() {
        when(springMongoGameRepository.findById(gameId1)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> mongoGameRepository.findGame(gameId1));

        verify(springMongoGameRepository, times(1)).findById(gameId1);
    }
}