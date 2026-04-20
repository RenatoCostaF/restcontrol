package com.restocontrol.restcontrol_api.dtos;

import com.restocontrol.restcontrol_api.entities.UserRole;

import java.util.UUID;

public record CreateUserResponseDTO(
        UUID id,
        String name,
        String email,
        UserRole role
) {
}
