package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.dtos.*;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.infra.exceptions.*;
import com.restocontrol.restcontrol_api.mappers.UserMapper;
import com.restocontrol.restcontrol_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<GetUserByNameResponseDTO> findByName(String name) {
        logger.info("Iniciando busca de usuários pelo nome: {}", name);
        var users = this.userRepository.findByName(name);
        if (users.isEmpty()) {
            logger.warn("Nenhum usuário encontrado para o nome informado: {}", name);
            throw new UsersNotFoundByNameException(name);
        }
        logger.info("Busca finalizada com {} usuário(s) encontrado(s) para o nome: {}", users.size(), name);
        return userMapper.toGetUserByNameResponseDTO(users);
    }

    @Transactional
    public CreateUserResponseDTO createUser(CreateUserRequestDTO userDto) {
        logger.info("Iniciando criação de usuário com login: {}", userDto.login());

        if (userRepository.existsByEmail(userDto.email())) {
            logger.warn("Falha ao criar usuário: e-mail já cadastrado para o login {}", userDto.login());
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.existsByLogin(userDto.login())) {
            logger.warn("Falha ao criar usuário: login já cadastrado: {}", userDto.login());
            throw new LoginAlreadyExistsException();
        }

        if (userDto.password().length() < 6) {
            logger.warn("Falha ao criar usuário: senha inválida para o login {}", userDto.login());
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        String encryptedPassword = passwordEncoder.encode(userDto.password());

        var user = userMapper.toEntity(userDto);
        user.setPassword(encryptedPassword);

        this.userRepository.save(user);

        logger.info("Usuário criado com sucesso. Id: {}, login: {}", user.getId(), user.getLogin());

        return userMapper.toCreateUserResponseDTO(user);
    }

    @Transactional
    public void updateUser(UpdateUserRequestDTO userDto, UUID id) {
        logger.info("Iniciando atualização do usuário de id: {}", id);
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        validateCurrentUserCanManageTarget(user);

        if (userDto.name() != null) {
            user.setName(userDto.name());
        }

        if (userDto.email() != null) {
            if (userRepository.existsByEmailAndIdNot(userDto.email(), id)) {
                logger.warn("Falha ao atualizar usuário {}: e-mail informado já está em uso", id);
                throw new EmailAlreadyExistsException();
            }
            user.setEmail(userDto.email());
        }

        if (userDto.login() != null) {
            if (userRepository.existsByLoginAndIdNot(userDto.login(), id)) {
                logger.warn("Falha ao atualizar usuário {}: login informado já está em uso", id);
                throw new LoginAlreadyExistsException();
            }
            user.setLogin(userDto.login());
        }

        if (userDto.address() != null) {
            user.setAddress(userDto.address());
        }

        user.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(user);

        logger.info("Atualização do usuário concluída com sucesso. Id: {}", id);

    }

    @Transactional
    public void deleteUser(UUID id) {
        logger.info("Iniciando remoção do usuário de id: {}", id);
        if (!userRepository.existsById(id)) {
            logger.warn("Falha ao remover usuário: id não encontrado {}", id);
            throw new UserNotFoundException();
        }

        this.userRepository.deleteById(id);
        logger.info("Usuário removido com sucesso. Id: {}", id);
    }

    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDto, UUID id) {
        logger.info("Iniciando alteração de senha para o usuário de id: {}", id);
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        validateCurrentUserCanManageTarget(user);

        if (changePasswordDto.password().length() < 6) {
            logger.warn("Falha ao alterar senha do usuário {}: senha inválida", id);
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        String encryptedPassword = passwordEncoder.encode(changePasswordDto.password());

        user.setPassword(encryptedPassword);
        user.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(user);

        logger.info("Senha alterada com sucesso para o usuário de id: {}", id);

    }

    private void validateCurrentUserCanManageTarget(User targetUser) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication is required to access this resource.");
        }

        boolean isRestaurantOwner = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_DONO_RESTAURANTE".equals(authority.getAuthority()));

        String authenticatedLogin = authentication.getName();
        boolean isOwner = authenticatedLogin != null && authenticatedLogin.equals(targetUser.getLogin());

        if (!isRestaurantOwner && !isOwner) {
            logger.warn("Acesso negado para o login {} ao tentar manipular o usuário {}", authenticatedLogin, targetUser.getId());
            throw new AccessDeniedException("You do not have permission to manage this user.");
        }
    }
}
