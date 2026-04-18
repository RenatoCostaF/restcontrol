package com.restocontrol.restcontrol_api.mappers;

import com.restocontrol.restcontrol_api.dtos.CreateUserRequestDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserResponseDTO;
import com.restocontrol.restcontrol_api.entities.User;

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

    public CreateUserResponseDTO toCreateUserResponseDTO(User user){
        return new CreateUserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
