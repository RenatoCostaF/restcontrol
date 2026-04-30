package com.restocontrol.restcontrol_api.mappers;

import com.restocontrol.restcontrol_api.dtos.CreateUserRequestDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserResponseDTO;
import com.restocontrol.restcontrol_api.dtos.GetUserByNameResponseDTO;
import com.restocontrol.restcontrol_api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User toEntity(CreateUserRequestDTO userDto);

    public abstract CreateUserResponseDTO toCreateUserResponseDTO(User user);

    @Mapping(target = "Address", source = "address")
    public abstract List<GetUserByNameResponseDTO> toGetUserByNameResponseDTO(List<User> users);

}
