package com.restocontrol.restcontrol_api.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum UserRole {
    DONO_RESTAURANTE("dono_restaurante"),
    CLIENTE("cliente");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }
    
    @JsonCreator
    public static UserRole fromValue(String value) {
        if (value == null) {
            return null;
        }
        
        return Arrays.stream(UserRole.values())
                .filter(role -> role.role.equalsIgnoreCase(value) || role.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ivalid role: " + value));
    }
}
