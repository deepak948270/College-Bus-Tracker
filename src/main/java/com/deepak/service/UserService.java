package com.deepak.service;

import com.deepak.entity.User;

public interface UserService {

    User createUser(User securityUser);

    User updateUser(User updatedUser);

    void deleteUser(String username);


}
