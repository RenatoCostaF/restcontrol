package com.restocontrol.restcontrol_api.controllers;

import com.restocontrol.restcontrol_api.dtos.ChangePasswordDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserRequestDTO;
import com.restocontrol.restcontrol_api.dtos.CreateUserResponseDTO;
import com.restocontrol.restcontrol_api.dtos.GetUserByNameResponseDTO;
import com.restocontrol.restcontrol_api.dtos.UpdateUserRequestDTO;
import com.restocontrol.restcontrol_api.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<GetUserByNameResponseDTO>> findByName(
            @PathVariable("name") String name
    ) {
        logger.info("GET -> /user" + name);
        var users = this.userService.findByName(name);
        if (users.size() > 0) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(
            @Valid @RequestBody CreateUserRequestDTO user
    ) {
        logger.info("POST -> /user");
        var response = this.userService.createUser(user);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody UpdateUserRequestDTO userDto
    ) {
        logger.info("PUT -> /users/" + id);
        this.userService.updateUser(userDto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") UUID id
    ) {
        logger.info("DELETE -> /users/" + id);
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<Void> changePassword(
            @PathVariable UUID id,
            @RequestBody ChangePasswordDTO changePasswordDto
    ){
        logger.info("PUT -> /users/change-password/"+id);
        this.userService.changePassword(changePasswordDto, id);
        return ResponseEntity.noContent().build();
    }
}