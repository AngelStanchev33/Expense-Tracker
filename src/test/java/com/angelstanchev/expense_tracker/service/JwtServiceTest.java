package com.angelstanchev.expense_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.angelstanchev.expense_tracker.service.impl.JwstServiceImpl;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void SetUp() {
        String testSecret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        long testExpiration = 86400000; // 24 часа в милисекунди

        jwtService = new JwstServiceImpl(testSecret, testExpiration);

    }

    @Test
    void generateToken_WithClams_ShoulCreateValidToken() {
        // arange
        String userEmail = "test@example.com";
        Map<String, Object> claims = Map.of("role", "USER", "userId", 123);

        // action
        String token = jwtService.generateToken(userEmail, claims);

        // assert
        assertNotNull(token);
        assertFalse(token.isEmpty());

    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String expectedUserEmail = "test@example.com";
        String token = jwtService.generateToken(expectedUserEmail);

        String userEmail = jwtService.extractUsername(token);

        assertEquals(expectedUserEmail, userEmail);

    }

}
