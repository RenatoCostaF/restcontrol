package com.restocontrol.restcontrol_api.infra.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String nome) {
        super("Usuário " + nome + " não encontrado");
    }
}
