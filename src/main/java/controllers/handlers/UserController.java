package controllers.handlers;

/*
import org.apache.catalina.User;
*/
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
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
        logger.info("/user/"+name);
        var sysUser = this.userService.findByName(name);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("id") Long id,
            @RequestBody User user
    ){
        logger.info("PUT => /user"+id);
        this.userService.updateUser(user,id);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody User user
    ){
        logger.info("POST => /user");
        this.userService.saveUser(user);
    }

    @PutMapping
    public ResponseEntity<Void> changePassword(){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id
    ){
        logger.info("DELETE => /user/"+id);
        this.userService.deleteUser(id);
    }

}
