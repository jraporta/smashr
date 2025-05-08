package com.jraporta.user.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {

    private String id;

    private String username;

    private String email;

    private String password;

}
