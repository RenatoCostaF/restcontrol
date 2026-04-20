package com.restocontrol.restcontrol_api.DTOs;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {
    @NotBlank("Password is required")
    String password;
}
