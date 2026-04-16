package com.restocontrol.restcontrol_api.repositories;

import com.restocontrol.restcontrol_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByName(String name);

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}
