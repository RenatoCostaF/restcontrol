package com.restocontrol.restcontrol_api.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6)
        String password) {
}
