package com.restocontrol.restcontrol_api.infra.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String nome) {
        super("Usuário " + nome + " não encontrado");
    }
}
