package controllers;

import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<User>> findByName(
            @PathVariable("name") String name
    ){
        /*logger.info("/users?name="+name);*/
        logger.info("/users/name="+name);
        List<User> userList = this.userService.findByName(name);
        if (userList.size() == 0) {
            return ResponseEntity.ok(userList);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody User user
    ){
        logger.info("POST -> /users");
        this.userService.createUser(user);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody User user
    ){
        logger.info("PUT -> /users/"+id);
        this.userService.updateUser(user, id);
        return ResponseEntity.ok().build();
    }
}