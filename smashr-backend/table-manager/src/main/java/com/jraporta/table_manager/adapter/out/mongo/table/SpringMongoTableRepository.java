package com.jraporta.table_manager.adapter.out.mongo.table;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringMongoTableRepository extends MongoRepository<TableDocument, String> {
}
