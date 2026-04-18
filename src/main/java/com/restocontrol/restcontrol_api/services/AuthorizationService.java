package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.dtos.AuthenticationDTO;
import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.infra.security.TokenService;
import com.restocontrol.restcontrol_api.mappers.AuthorizationMapper;
import com.restocontrol.restcontrol_api.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthorizationMapper authorizationMapper;

    public AuthorizationService(UserRepository repository, AuthenticationManager authenticationManager, TokenService tokenService, AuthorizationMapper authorizationMapper) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authorizationMapper = authorizationMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return authorizationMapper.toLoginResponseDTO(token);
    }


}
