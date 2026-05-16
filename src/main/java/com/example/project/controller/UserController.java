package com.example.project.controller;

import com.example.project.dto.UserRegisterRequest;
import com.example.project.dto.UserRegisterResponse;
import com.example.project.model.User;
import com.example.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserRegisterResponse> register(
        @Valid @RequestBody UserRegisterRequest request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
