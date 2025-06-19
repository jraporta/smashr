package com.jraporta.game.manager.adapter.out.mongo;

import com.jraporta.game.manager.domain.model.game.Game;
import com.jraporta.game.manager.domain.port.out.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class MongoGameRepository implements GameRepository {

    private final SpringMongoGameRepository springMongoGameRepository;


    @Override
    public List<Game> findAll() {
        return springMongoGameRepository.findAll().stream().map(GameDocument::toDomain).toList();
    }

    @Override
    public Game saveGame(Game game) {
        return springMongoGameRepository
                .save(new GameDocument(game))
                .toDomain();
    }

    @Override
    public Game findGame(String id) {
        return springMongoGameRepository.findById(id).orElseThrow().toDomain();
    }

    @Override
    public List<Game> getGamesFiltered(String tableId, LocalDateTime from, LocalDateTime to) {
        return springMongoGameRepository.findAllByTable(tableId).stream().map(GameDocument::toDomain).toList();
    }

}
