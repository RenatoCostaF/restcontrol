package com.restocontrol.restcontrol_api.controllers;

import com.restocontrol.restcontrol_api.dtos.*;
import com.restocontrol.restcontrol_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "Users", description = "Controller para CRUD de usuários")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            description = "Buscar usuário por nome",
            summary = "Buscar usuário",
            responses = {
                    @ApiResponse(description = "Sucesso - Retorna os dados definidos em GetUserByNameResponseDTO", responseCode = "200")
            }
    )
    @GetMapping("/{name}")
    public ResponseEntity<List<GetUserByNameResponseDTO>> findByName(
            @PathVariable("name") String name
    ) {
        logger.info("Recebida requisição para buscar usuários pelo nome: {}", name);
        var users = this.userService.findByName(name);
        logger.info("Busca por nome concluída com {} usuário(s) encontrado(s) para: {}", users.size(), name);
        return ResponseEntity.ok(users);
    }

    @Operation(
            description = "Criar novo usuário na base de dados. Realiza validação para confirmar login e email únicos",
            summary = "Criar novo usuário",
            responses = {
                    @ApiResponse(description = "Sucesso - Retorna os dados definidos em CreateUserResponseDTO", responseCode = "201 - Created")
            }
    )
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(
            @Valid @RequestBody CreateUserRequestDTO user
    ) {
        logger.info("Recebida requisição para criar usuário com login: {}", user.login());
        var response = this.userService.createUser(user);
        logger.info("Usuário criado com sucesso. Id: {}", response.id());
        return ResponseEntity.status(201).body(response);
    }


    @Operation(
            description = "Realizar atualização de dados gerais do usuário. Não realiza atualização de password",
            summary = "Atualização de usuário",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody UpdateUserRequestDTO userDto
    ) {
        logger.info("Recebida requisição para atualizar o usuário de id: {}", id);
        this.userService.updateUser(userDto, id);
        logger.info("Atualização do usuário concluída com sucesso. Id: {}", id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Remover usuário através do Id",
            summary = "Remover usuário",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "204")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") UUID id
    ) {
        logger.info("Recebida requisição para remover o usuário de id: {}", id);
        this.userService.deleteUser(id);
        logger.info("Usuário removido com sucesso. Id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Alterar senha do usuário definido pelo Id",
            summary = "Alterar senha",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "204")
            }
    )
    @PutMapping("/change-password/{id}")
    public ResponseEntity<Void> changePassword(
            @PathVariable UUID id,
            @RequestBody ChangePasswordDTO changePasswordDto
    ) {
        logger.info("Recebida requisição para alterar a senha do usuário de id: {}", id);
        this.userService.changePassword(changePasswordDto, id);
        logger.info("Senha do usuário alterada com sucesso. Id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
