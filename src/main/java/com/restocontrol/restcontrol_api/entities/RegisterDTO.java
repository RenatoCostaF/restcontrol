package com.restocontrol.restcontrol_api.entities;

public record RegisterDTO(String login, String password, UserRole role) {
}
