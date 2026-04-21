package com.restocontrol.restcontrol_api.dtos;

public record UpdateUserRequestDTO(
        String name,
        String email,
        String login,
        String address
) {
}
