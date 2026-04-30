package com.restocontrol.restcontrol_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(
        @NotBlank(message = "Password is required")
        String password) {
}
