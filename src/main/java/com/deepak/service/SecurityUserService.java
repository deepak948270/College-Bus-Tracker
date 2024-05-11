package com.deepak.service;

import com.deepak.entity.SecurityUser;

public interface SecurityUserService {

    SecurityUser createUser(SecurityUser securityUser);

    SecurityUser updateUser(SecurityUser updatedUser);

    void deleteUser(String username);


}
