package com.restocontrol.restcontrol_api.infra.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getLocalizedMessage()
        );
        problemDetail.setTitle("Email Already Exists");

        return problemDetail;
    }

    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ProblemDetail handleLoginAlreadyExists(LoginAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getLocalizedMessage()
        );
        problemDetail.setTitle("Login Already Exists");

        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.NOT_FOUND
        );
        problemDetail.setDetail("There is no user with the given id.");

        return problemDetail;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handleInvalidPassword(InvalidPasswordException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid Password");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ProblemDetail handleAthenticationFailed(AuthenticationFailedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Authentication Failed");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ProblemDetail handleTokenGeneration(TokenGenerationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Token Generation Failed");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(InvalidOrExpiredTokenException.class)
    public ProblemDetail handleInvalidOrExpiredToken(InvalidOrExpiredTokenException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Invalid or Expired Token");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(UsersNotFoundByNameException.class)
    public ProblemDetail handleUsersNotFoundByName(UsersNotFoundByNameException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Users Not Found");
        problemDetail.setDetail("No users were found for requested name.");
        problemDetail.setProperty("name", e.getSearchedName());

        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, List<String>> fieldErrors = new LinkedHashMap<>();

        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = error instanceof FieldError fieldError ? fieldError.getField() : error.getObjectName();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(errorMessage);
                });

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("DTO Validation Failed");
        problemDetail.setDetail("One or more DTO fields are invalid.");
        problemDetail.setProperty("errors", fieldErrors);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ProblemDetail handleInvalidUserRole(InvalidUserRoleException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid User Role");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Access Denied");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntime(RuntimeException e) {
        log.error("Unhandled runtime exception", e);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("An unexpected error occurred");

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException (Exception e) {
        log.error("Unhandled exception", e);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("An unexpected error occured");

        return problemDetail;
    }

}
