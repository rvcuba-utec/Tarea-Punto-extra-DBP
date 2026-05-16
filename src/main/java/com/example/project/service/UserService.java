package com.example.project.service;

import com.example.project.dto.UserRegisterRequest;
import com.example.project.dto.UserRegisterResponse;
import com.example.project.exception.BadRequestException;
import com.example.project.exception.ConflictException;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);
        return new UserRegisterResponse(user.getId());
    }

    public User getById(Long id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("User not found"));
    }
}
