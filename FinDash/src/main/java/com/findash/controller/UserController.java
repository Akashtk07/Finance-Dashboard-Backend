package com.findash.controller;


import com.findash.dto.ApiResponse;
import com.findash.dto.UserRequestDTO;
import com.findash.model.User;
import com.findash.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody UserRequestDTO dto) {

        User user = service.createUser(dto);

        ApiResponse<User> response = new ApiResponse<>(
                true,
                "User created successfully",
                user
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUsers(){

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "Users fetched successfully",
                service.getAllUsers()
        );

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Updated", service.updateUser(id, dto))
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<User>> toggleStatus(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Status updated", service.toggleStatus(id))
        );
    }
}