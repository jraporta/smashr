package com.jraporta.game.manager.adapter.out.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringMongoGameRepository extends MongoRepository<GameDocument, String> {
    List<GameDocument> findAllByTable(String tableId);
}
