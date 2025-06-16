package com.jraporta.game.manager.adapter.out.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringMongoGameRepository extends MongoRepository<GameDocument, String> {
}
