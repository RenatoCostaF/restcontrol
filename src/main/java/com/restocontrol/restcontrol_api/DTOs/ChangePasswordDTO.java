package com.restocontrol.restcontrol_api.DTOs;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO (
        @NotBlank(message = "Password is required")
        String password){
}
