package com.restocontrol.restcontrol_api.infra.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("A senha precisa conter pelo menos 6 caracteres");
    }
}
