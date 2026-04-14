package com.restocontrol.restcontrol_api.infra.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException() {
        super("Já existe um usuário cadastrado com o email informado");
    }
}
