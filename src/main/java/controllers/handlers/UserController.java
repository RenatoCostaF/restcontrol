package controllers.handlers;

/*
import org.apache.catalina.User;
*/
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<User>> findByName(
            @PathVariable("name") String name
    ){
        logger.info("/user/"+name);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(){
    }

    @PostMapping
    public ResponseEntity<Void> createUser(){

    }

    @PutMapping
    public ResponseEntity<Void> changePassword(){

    }

    public ResponseEntity<Void> deleteUser(){

    }

}
