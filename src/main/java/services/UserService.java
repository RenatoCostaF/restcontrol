package services;

import entities.User;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findByName(String name){
        return this.userRepository.findByName(name);
    }

    public void updateUser(User user, Long id){
        var update = this.userRepository.update(user,id);
    }

    public void saveUser(User user){
        var save = this.userRepository.save(user);
    }

    public void deleteUser(Long id){
        var delete = this.userRepository.delete(id);
    }

}
