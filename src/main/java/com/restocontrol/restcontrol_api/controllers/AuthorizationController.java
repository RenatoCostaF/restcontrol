package com.restocontrol.restcontrol_api.controllers;

import com.restocontrol.restcontrol_api.dtos.AuthenticationDTO;
import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import com.restocontrol.restcontrol_api.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {

        LoginResponseDTO response = service.login(data);

        return ResponseEntity.ok(response);
    }

}
