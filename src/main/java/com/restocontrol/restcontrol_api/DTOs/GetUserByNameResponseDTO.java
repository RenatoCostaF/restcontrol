package com.restocontrol.restcontrol_api.dtos;

public record GetUserByNameResponseDTO(
        String id,
        String name,
        String email,
        String Address,
        String role
) {
}
