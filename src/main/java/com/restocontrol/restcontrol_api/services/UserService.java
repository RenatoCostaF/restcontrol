package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.dtos.ChangePasswordDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserRequestDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserResponseDTO;
import com.restocontrol.restcontrol_api.dtos.GetUserByNameResponseDTO;
import com.restocontrol.restcontrol_api.dtos.UpdateUserRequestDTO;
import com.restocontrol.restcontrol_api.infra.exceptions.*;
import com.restocontrol.restcontrol_api.mappers.UserMapper;
import com.restocontrol.restcontrol_api.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<GetUserByNameResponseDTO> findByName(String name) {
        var users = this.userRepository.findByName(name);
        if (users.isEmpty()) {
            throw new UsersNotFoundByNameException(name);
        }
        return userMapper.toGetUserByNameResponseDTO(users);
    }

    @Transactional
    public CreateUserResponseDTO createUser(CreateUserRequestDTO userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.existsByLogin(userDto.login())) {
            throw new LoginAlreadyExistsException();
        }

        if (userDto.password().length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        String encryptedPassword = passwordEncoder.encode(userDto.password());

        var user = userMapper.toEntity(userDto);
        user.setPassword(encryptedPassword);

        this.userRepository.save(user);

        return userMapper.toCreateUserResponseDTO(user);
    }

    @Transactional
    public void updateUser(UpdateUserRequestDTO userDto, UUID id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (userDto.name() != null) {
            user.setName(userDto.name());
        }

        if (userDto.email() != null) {
            if (userRepository.existsByEmail(userDto.email())) {
                throw new EmailAlreadyExistsException();
            }
            user.setEmail(userDto.email());
        }

        if (userDto.login() != null) {
            if (userRepository.existsByLogin(userDto.login())) {
                throw new LoginAlreadyExistsException();
            }
            user.setLogin(userDto.login());
        }

        if (userDto.address() != null) {
            user.setAddress(userDto.address());
        }

        user.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(user);

    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        this.userRepository.deleteById(id);
    }

    public void changePassword(ChangePasswordDTO changePasswordDto, UUID id){
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (changePasswordDto.password().length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        String encryptedPassword = passwordEncoder.encode(changePasswordDto.password());

        user.setPassword(encryptedPassword);
        user.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(user);

    }
}