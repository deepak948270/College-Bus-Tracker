package com.deepak.service;

import java.util.List;
import java.util.Optional;

import com.deepak.entity.User;

public interface UserService {

    User createUser(User securityUser);

    User updateUser(User updatedUser);

    void deleteUser(String username);

    List<User> findAllUsers();

    Optional<User> findUserByUsername(String username);

}
