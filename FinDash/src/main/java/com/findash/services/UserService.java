package com.findash.services;


import com.findash.dto.UserRequestDTO;
import com.findash.model.User;
import com.findash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDTO dto) {


        if (repo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }


        if (dto.getPhone() != null && repo.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("User with this phone already exists");
        }


        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return repo.save(user);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User updateUser(Long id, UserRequestDTO dto) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        return repo.save(user);
    }

    public User toggleStatus(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(!user.isActive());
        return repo.save(user);
    }
}

