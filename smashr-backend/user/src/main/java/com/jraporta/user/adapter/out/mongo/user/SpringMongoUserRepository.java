package com.jraporta.user.adapter.out.mongo.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringMongoUserRepository extends MongoRepository<UserDocument, String> {

    @Query("{$or: [{'email': ?0}, {'username': ?0}]}")
    Optional<UserDocument> findByEmailOrUsername(String emailOrUsername);

}
