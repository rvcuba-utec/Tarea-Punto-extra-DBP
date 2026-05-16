package com.example.project.dto;

import jakarta.validation.constraints.*;

public class UserRegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
        regexp = "^[A-Z][a-zA-Z]*$",
        message = "First name must start with uppercase"
    )
    private String firstName;

    @NotBlank
    @Pattern(
        regexp = "^[A-Z][a-zA-Z]*$",
        message = "Last name must start with uppercase"
    )
    private String lastName;

    @NotBlank
    @Size(min = 8)
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
        message = "Password must contain at least one letter and one number"
    )
    private String password;

    public UserRegisterRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
