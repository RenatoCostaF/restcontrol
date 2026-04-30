package com.restocontrol.restcontrol_api.mappers;

import com.restocontrol.restcontrol_api.dtos.LoginResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {

    public abstract LoginResponseDTO toLoginResponseDTO(String token);
}
