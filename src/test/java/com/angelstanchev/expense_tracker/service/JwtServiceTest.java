package com.angelstanchev.expense_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void generateToken_WithClaims_ShouldThrowIfEmailIsNull() {
        String userEmail = null;
        Map<String, Object> claims = Map.of("role", "USER");

        assertThrows(IllegalArgumentException.class, () -> {
            jwtService.generateToken(userEmail, claims);
        });
    }

    @Test
    void generateToken_WithClaims_ShouldThrowIfEmailIsEmpty() {
        String userEmail = "";
        Map<String, Object> claims = Map.of("role", "USER");

        assertThrows(IllegalArgumentException.class, () -> {
            jwtService.generateToken(userEmail, claims);
        });
    }

    @Test
    void generateToken_WithClaims_ShouldThrowIfEmailIsOnlyWhitespace() {
        String userEmail = "   ";
        Map<String, Object> claims = Map.of("role", "USER");

        assertThrows(IllegalArgumentException.class, () -> {
            jwtService.generateToken(userEmail, claims);
        });
    }

    @Test
    void generateToken_WithOutClaiim_ShouldCreateValidToken() {
        String userEmail = "test@example.com";
        String token = jwtService.generateToken(userEmail);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String extractUserName = jwtService.extractUsername(token);
        assertEquals(userEmail, extractUserName);

    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        String token = jwtService.generateToken("test@angel.com");

        boolean tokenValid = jwtService.isTokenValid(token);

        assertTrue(tokenValid);
    }

    @Test
    void isTokenValid_WithInvalidToken_ShouldReturnFalse() {
        String token = "ivalid.token";

        boolean tokenValid = jwtService.isTokenValid(token);

        assertFalse(tokenValid);
    }

    @Test
    void isTokenValid_WithNullToken_ShouldReturnFalse() {

        boolean tokenValid = jwtService.isTokenValid(null);

        assertFalse(tokenValid);

    }

    @Test
    void isTokenValid_WithEmptyToken_ShouldReturnFalse() {
        String token = "";

        boolean tokenValid = jwtService.isTokenValid(token);

        assertFalse(tokenValid);
    }

    @Test
    void generateToken_ShouldCreateDifferentTokensEachTime() {
        String token = jwtService.generateToken("test@angel.com");
        String token2 = jwtService.generateToken("test2@angel.com");

        assertNotEquals(token, token2);

    }

    @Test
    void isTokenValid_WithExpiredToken_ShouldReturnFalse() {
        String testSecret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        JwtService shortExpJwtService = new JwstServiceImpl(testSecret, 0);
        String token = shortExpJwtService.generateToken("angel@test.com");

        boolean tokenValid = shortExpJwtService.isTokenValid(token);

        assertFalse(tokenValid);

    }
}
