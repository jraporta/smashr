package com.jraporta.user.adapter.out.mongo.user;

import com.jraporta.user.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    public User toDomain() {
        return new User(id, username, email, password);
    }

}
