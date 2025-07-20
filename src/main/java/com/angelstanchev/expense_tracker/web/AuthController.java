package com.angelstanchev.expense_tracker.web;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angelstanchev.expense_tracker.model.dto.AuthRequestDto;
import com.angelstanchev.expense_tracker.model.dto.AuthResponseDto;
import com.angelstanchev.expense_tracker.service.JwtService;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
            UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            UserDetails user = (UserDetails) authentication.getPrincipal();

            List<String> userRoles = user.getAuthorities().stream()
                    .map(Object::toString)
                    .toList();

            Map<String, Object> claims = Map.of("roles", userRoles);

            String token = jwtService.generateToken(user.getUsername(), claims);

            return ResponseEntity.ok(new AuthResponseDto(token));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", "Bad credentials", 
                            "message", "Invalid username or password"
                    ));
        }
    }
}
