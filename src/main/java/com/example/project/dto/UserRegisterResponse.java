package com.example.project.dto;

public class UserRegisterResponse {

    private Long id;

    public UserRegisterResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
