package com.restocontrol.restcontrol_api.repositories;

import com.restocontrol.restcontrol_api.entities.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepositoryImp(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<User> findByName(String name){
        return this.jdbcClient
                .sql("SELECT * FROM people WHERE Name = :name")
                .param("name", name)
                .query(User.class)
                .optional();
    }

    @Override
    public Integer save(User user){
        return jdbcClient
                .sql("INSERT INTO users (name, email, login, password, address, type) VALUES (:name, :email, :login, :password, :address, :type)")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("address", user.getAddress())
                .param("type", user.getType());
    }
    Integer changePassword(Long id, String password); //Trocar senha

    @Override
    public Integer update(User user, Long id){
        return jdbcClient
                .sql("UPDATE users SET name = :name, email = :email, login = :login, address = :address, type = :type")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("address", user.getAddress())
                .param("type", user.getType())
                .update();
    }
    Integer delete(Long id);//Remove usuario existente
}
