package com.example.project.controller;

import com.example.project.dto.AuthRequest;
import com.example.project.dto.AuthResponse;
import com.example.project.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
