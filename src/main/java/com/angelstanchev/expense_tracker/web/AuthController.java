package com.angelstanchev.expense_tracker.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angelstanchev.expense_tracker.model.dto.AuthRequestDto;
import com.angelstanchev.expense_tracker.model.dto.AuthResponseDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request ) {
        //TODO: process POST request
        
        return ResponseEntity.ok(new AuthResponseDto("example token"));
    }
    
    
}
