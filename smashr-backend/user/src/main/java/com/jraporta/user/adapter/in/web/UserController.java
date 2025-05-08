package com.jraporta.user.adapter.in.web;

import com.jraporta.user.adapter.in.web.dto.LoginRequest;
import com.jraporta.user.application.usecase.LoginUseCase;
import com.jraporta.user.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/user/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.login(request.getUsername(), request.getPassword()));
    }

}
