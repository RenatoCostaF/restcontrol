package com.restocontrol.restcontrol_api.infra.exceptions;

public class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String role) {
        super("Invalid role: " + role);
    }
}
