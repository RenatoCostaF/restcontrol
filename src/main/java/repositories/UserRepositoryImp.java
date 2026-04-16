package br.com.fiap.tech_challege.tech_challenge.repositories;

import br.com.fiap.tech_challege.tech_challenge.entities.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //Indicar que a classe está apta para realizar a injeção de dependencias
public class UserRepositoryImp implements br.com.fiap.tech_challege.tech_challenge.repositories.UserRepository {

    private final JdbcClient jdbcClient;
    public UserRepositoryImp (JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public Optional<User> findByName(String name){
        return this.jdbcClient.sql("SELECT * FROM users WHERE name = :name")
                .param("name", name)
                .query(User.class)
                .optional();
    }

    /*@Override
    public Optional<User> findByName(String name) {
        return this.jdbcClient.sql("SELECT * FROM users WHERE name = :name")
                .param("name", name)
                .query(User.class)
                .optional();
    }*/

    /*@Override
    public List<User> findAll(int size, int offset) {
        return List.of();
    }*/

    @Override
    public Integer createUser(User user) {
        return this.jdbcClient
                .sql("INSERT INTO users (name, email, address, type) VALUES (:name, :email, :address, :type)")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("address", user.getAddress())
                .param("type", user.getType())
                .update();
    }

    @Override
    public Integer updateUser(User user, Long id) {
        return this.jdbcClient
                .sql("UPDATE users SET name = :name, email = :email, address = :address, type = :type")
                .param("id",id)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("address", user.getAddress())
                .param("type", user.getType())
                .update();
    }

    /*@Override
    public Integer deleteUser(Long id) {
        return 0;
    }*/
}