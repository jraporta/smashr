package com.jraporta.user.domain.port.out;

import com.jraporta.user.domain.model.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmailOrUsername(String username);
    boolean userExists(String userId);

}
