package com.deepak.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.entity.SecurityUser;
import com.deepak.service.SecurityUserService;

@RestController
public class UserController {

    private final SecurityUserService securityUserService;

    public UserController(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    // Update endpoint
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody SecurityUser updateUser) {
        try {

            System.out.println(updateUser);
            // Call a method in UserService to update the user
            securityUserService.updateUser(updateUser);
            return ResponseEntity.ok("User updated successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user.");
        }
    }

    // Delete endpoint
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody SecurityUser deleteUser) {
        try {
            // Call a method in UserService to delete the user
            securityUserService.deleteUser(deleteUser.getUsername());
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

}
