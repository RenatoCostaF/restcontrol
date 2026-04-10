package com.restocontrol.restcontrol_api.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String nome) {
        super("Usuário " + nome + " não encontrado");
    }
}
