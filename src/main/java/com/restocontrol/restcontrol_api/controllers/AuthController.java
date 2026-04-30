package com.restocontrol.restcontrol_api.controllers;

import com.restocontrol.restcontrol_api.dtos.AuthenticationDTO;
import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import com.restocontrol.restcontrol_api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticação e emissão de token JWT")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza autenticação com login e senha e retorna um token JWT para uso nos endpoints protegidos.",
            security = {}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticação realizada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payload inválido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "validation-error",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "DTO Validation Failed",
                                              "status": 400,
                                              "detail": "One or more DTO fields are invalid.",
                                              "instance": "/auth/login",
                                              "errors": {
                                                "login": ["Login is required"],
                                                "password": ["size must be between 6 and 2147483647"]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Login ou senha inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "authentication-failed",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Authentication Failed",
                                              "status": 401,
                                              "detail": "Invalid username or password",
                                              "instance": "/auth/login"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Falha inesperada ao autenticar",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        logger.info("Recebida requisição de autenticação para o login: {}", data.login());

        LoginResponseDTO response = service.login(data);
        logger.info("Autenticação concluída com sucesso para o login: {}", data.login());

        return ResponseEntity.ok(response);
    }

}
