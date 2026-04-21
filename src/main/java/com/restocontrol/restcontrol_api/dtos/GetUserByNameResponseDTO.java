package com.restocontrol.restcontrol_api.dtos;

import com.restocontrol.restcontrol_api.entities.UserRole;

public record GetUserByNameResponseDTO(
        String id,
        String name,
        String email,
        String Address,
        UserRole role
) {
}
