package com.restocontrol.restcontrol_api.DTOs;

import com.restocontrol.restcontrol_api.entities.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
