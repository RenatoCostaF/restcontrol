package com.restocontrol.restcontrol_api.repositories;

import com.restocontrol.restcontrol_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByName(String name);

    User findByEmail(String email);

    User findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByLoginAndIdNot(String login, UUID id);
}
