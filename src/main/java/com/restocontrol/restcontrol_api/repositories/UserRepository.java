package com.restocontrol.restcontrol_api.repositories;

import com.restocontrol.restcontrol_api.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByName(String name);

    UserDetails findByEmail(String email);

    UserDetails findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
