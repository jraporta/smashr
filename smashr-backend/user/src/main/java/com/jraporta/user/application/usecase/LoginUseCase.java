package com.jraporta.user.application.usecase;

import com.jraporta.user.domain.model.user.User;

public interface LoginUseCase {

    User login(String username, String password);

}
