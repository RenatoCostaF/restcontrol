package br.com.fiap.tech_challege.tech_challenge.services;

import br.com.fiap.tech_challege.tech_challenge.entities.User;
import br.com.fiap.tech_challege.tech_challenge.repositories.UserRepository;
import org.springframework.stereotype.Service;
package com.restocontrol.restcontrol_api.services;

import com.restocontrol.restcontrol_api.entities.User;
import com.restocontrol.restcontrol_api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findByName(String name){
        return this.userRepository.findByName(name);
    }

    @Transactional
    public void createUser(User user){
        this.userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, UUID id){
        user.setId(id);
        this.userRepository.save(user);
    }

    public void deleteUser(UUID id){
        this.userRepository.deleteById(id);
    }
}