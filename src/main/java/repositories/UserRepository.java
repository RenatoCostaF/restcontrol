package br.com.fiap.tech_challege.tech_challenge.repositories;

import br.com.fiap.tech_challege.tech_challenge.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByName(String name);
    /*List<User> findAll(int size, int offset);*/
    Integer createUser(User user); //Retorna a quantidade de linhas criadas
    Integer updateUser(User user, Long id);
    /*Integer deleteUser(Long id);*/
}