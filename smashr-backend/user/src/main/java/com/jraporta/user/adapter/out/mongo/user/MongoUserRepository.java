package com.jraporta.user.adapter.out.mongo.user;

import com.jraporta.user.domain.model.user.User;
import com.jraporta.user.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class MongoUserRepository implements UserRepository {

    private final SpringMongoUserRepository springMongoUserRepository;

    @Override
    public Optional<User> findByEmailOrUsername(String emailOrUsername) {
        return springMongoUserRepository.findByEmailOrUsername(emailOrUsername).map(UserDocument::toDomain);
    }

    @Override
    public boolean userExists(String userId) {
        return springMongoUserRepository.existsById(userId);
    }
}
