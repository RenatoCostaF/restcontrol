package com.restocontrol.restcontrol_api.controllers.handlers;

import com.restocontrol.restcontrol_api.DTOs.AuthenticationDTO;
import com.restocontrol.restcontrol_api.DTOs.LoginResponseDTO;
import com.restocontrol.restcontrol_api.DTOs.RegisterDTO;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restocontrol.restcontrol_api.repositories.UserRepository;
import com.restocontrol.restcontrol_api.services.AuthorizationService;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Autowired
    private AuthorizationService service;

    @PostMapping("/email")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        String token = service.login(data);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){

        service.register(data);

        return ResponseEntity.status(201).build();
    }
}
