package com.restocontrol.restcontrol_api.dtos;

import com.restocontrol.restcontrol_api.entities.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Login is required")
        String login,

        @NotBlank(message = "Password is required")
        @Size(min = 6)
        String password,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "Role is required")
        UserRole role) {
}
