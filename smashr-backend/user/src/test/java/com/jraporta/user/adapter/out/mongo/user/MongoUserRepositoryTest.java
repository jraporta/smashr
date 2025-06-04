package com.jraporta.user.adapter.out.mongo.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoUserRepositoryTest {

    @Mock
    private SpringMongoUserRepository springMongoUserRepository;

    @InjectMocks
    private MongoUserRepository mongoUserRepository;

    @Test
    void findByEmailOrUsername_shouldMapUserDocumentToUser() {
        String emailOrUsername = "someString";
        UserDocument userDocument = new UserDocument("someId", emailOrUsername, "someEmail", "pass");
        when(springMongoUserRepository.findByEmailOrUsername(emailOrUsername)).thenReturn(Optional.of(userDocument));

        assertEquals(Optional.of(userDocument).map(UserDocument::toDomain), mongoUserRepository.findByEmailOrUsername(emailOrUsername));

        verify(springMongoUserRepository, times(1)).findByEmailOrUsername(emailOrUsername);
    }

    @Test
    void findByEmailOrUsername_noUserWithSuchUsername_shouldReturnEmptyOptional() {
        String emailOrUsername = "someString";
        when(springMongoUserRepository.findByEmailOrUsername(emailOrUsername)).thenReturn(Optional.empty());

        assertTrue(mongoUserRepository.findByEmailOrUsername(emailOrUsername).isEmpty());

        verify(springMongoUserRepository, times(1)).findByEmailOrUsername(emailOrUsername);
    }
}