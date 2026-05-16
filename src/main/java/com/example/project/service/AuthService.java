package com.example.project.service;

import com.example.project.dto.AuthRequest;
import com.example.project.dto.AuthResponse;
import com.example.project.exception.UnauthorizedException;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Unknown email"));

        if (
            !passwordEncoder.matches(request.getPassword(), user.getPassword())
        ) {
            throw new UnauthorizedException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
