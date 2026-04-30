package com.restocontrol.restcontrol_api.infra.security;

import com.restocontrol.restcontrol_api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);
        if (token != null) {
            logger.debug("Token Bearer identificado para a requisição: {} {}", request.getMethod(), request.getRequestURI());
            try {
                var login = tokenService.validateToken(token);
                UserDetails user = userRepository.findByLogin(login);
                if (user == null) {
                    logger.warn("Usuário do token não encontrado para o login: {}", login);
                    throw new UsernameNotFoundException("User not found for login: " + login);
                }

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Contexto de segurança preenchido com sucesso para o login: {}", login);
            } catch (RuntimeException e) {
                logger.warn("Falha ao autenticar a requisição {} {} via token: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
                SecurityContextHolder.clearContext();
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}
