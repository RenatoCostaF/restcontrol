package com.restocontrol.restcontrol_api.controllers;

import com.restocontrol.restcontrol_api.dtos.AuthenticationDTO;
import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import com.restocontrol.restcontrol_api.services.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        logger.info("Recebida requisição de autenticação para o login: {}", data.login());

        LoginResponseDTO response = service.login(data);
        logger.info("Autenticação concluída com sucesso para o login: {}", data.login());

        return ResponseEntity.ok(response);
    }

}
