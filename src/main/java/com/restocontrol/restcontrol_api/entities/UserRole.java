package com.restocontrol.restcontrol_api.entities;

public enum UserRole {
    DONO_RESTAURANTE("dono_restaurante"),
    CLIENTE("cliente");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
