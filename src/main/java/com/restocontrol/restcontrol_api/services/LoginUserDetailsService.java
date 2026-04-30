package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(LoginUserDetailsService.class);

    private final UserRepository userRepository;

    public LoginUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info("Carregando detalhes do usuário para o login: {}", login);
        var user = userRepository.findByLogin(login);
        if (user == null) {
            logger.warn("Nenhum usuário encontrado para o login informado: {}", login);
            throw new UsernameNotFoundException("User not found for login: " + login);
        }

        logger.info("Detalhes do usuário carregados com sucesso para o login: {}", login);
        return user;
    }
}
