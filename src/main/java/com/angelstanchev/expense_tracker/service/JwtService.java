package com.angelstanchev.expense_tracker.service;

import java.util.Map;

public interface JwtService {
    String generateToken(String userEmail, Map<String, Object> claims);
    
    String generateToken(String username);
    String extractUsername(String token);
    boolean isTokenValid(String token);
} 