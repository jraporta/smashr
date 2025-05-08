package com.jraporta.user.application.usecase;

import com.jraporta.user.application.usecase.exception.BadCredentialsException;
import com.jraporta.user.domain.model.user.User;
import com.jraporta.user.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class LoginService implements LoginUseCase{

    private final UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByEmailOrUsername(username);

        if (userOpt.isPresent() && password.equals(userOpt.get().getPassword())) {
            return userOpt.get();
        }

        throw new BadCredentialsException("Bad credentials");

    }
}
