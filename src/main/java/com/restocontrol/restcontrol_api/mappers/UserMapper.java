package com.restocontrol.restcontrol_api.mappers;

import com.restocontrol.restcontrol_api.dtos.CreateUserRequestDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserResponseDTO;
import com.restocontrol.restcontrol_api.dtos.GetUserByNameResponseDTO;
import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.entities.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequestDTO userDto) {
        var user = new User();

        user.setLogin(userDto.login());
        user.setPassword(userDto.password());
        user.setEmail(userDto.email());
        user.setName(userDto.name());
        user.setAddress(userDto.address());
        user.setRole(userDto.role());

        return user;
    }

    public CreateUserResponseDTO toCreateUserResponseDTO(User user) {
        return new CreateUserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public List<GetUserByNameResponseDTO> toGetUserByNameResponseDTO(List<User> users) {
        return users.stream()
                .map(user -> new GetUserByNameResponseDTO(
                        user.getId().toString(),
                        user.getName(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getRole()
                ))
                .toList();
    }
}