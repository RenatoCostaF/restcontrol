package com.restocontrol.restcontrol_api.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailInvalidoException.class)
    public ProblemDetail handleEmailInvalido(EmailInvalidoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Email Inválido Inserido");
        problemDetail.setDetail("O email informado não está dentro do padrão: nome@dominio");

        return problemDetail;
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ProblemDetail handleEmailJaCadastrado(EmailJaCadastradoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getLocalizedMessage());
        problemDetail.setTitle("Email Já Cadastrado");

        return problemDetail;
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ProblemDetail handleSenhaIncorretaException(SenhaIncorretaException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Senha Incorreta Informada");
        problemDetail.setDetail("A senha informada não condiz com a senha cadastrada para esse email");

        return problemDetail;
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ProblemDetail handleSenhaInvalidaException(SenhaInvalidaException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Senha Inválida Informada");
        problemDetail.setDetail("A senha informada contém uma quantidade de caracteres abaixo do mínimo necessário (8)");

        return problemDetail;
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setTitle("Usuário Não Encontrado");

        return problemDetail;
    }
}
