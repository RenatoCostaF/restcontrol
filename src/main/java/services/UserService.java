package br.com.fiap.tech_challege.tech_challenge.services;

import br.com.fiap.tech_challege.tech_challenge.entities.User;
import br.com.fiap.tech_challege.tech_challenge.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Optional<User> findByName(String name){
        return this.userRepository.findByName(name);
    }
    /*public Optional<User> findByName(String name){
        return this.userRepository.findByName(name);
    }*/

    public void createUser(User user){
        var save = this.userRepository.createUser(user);
        Assert.state(save == 1, "Erro ao salvar usuario "+user.getName());
    }

    public void updateUser(User user, Long id){
        var update = this.userRepository.updateUser(user, id);
        if(update == 0){
            throw new RuntimeException("Usuario nao encontrado!");
        }
    }

    /*public void deleteUser(Long id){
        var delete = this.userRepository.deleteUser(id);
        if(delete == 0){
            throw new RuntimeException("Nao foi possivel remover usuario "+id);
        }
    }*/
}