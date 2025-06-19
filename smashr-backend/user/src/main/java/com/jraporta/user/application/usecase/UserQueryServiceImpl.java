package com.jraporta.user.application.usecase;

import com.jraporta.user.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;

    @Override
    public boolean userExists(String userId) {
        return userRepository.userExists(userId);
    }
}
