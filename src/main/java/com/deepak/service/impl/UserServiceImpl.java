package com.deepak.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deepak.entity.User;
import com.deepak.repository.UserRepository;
import com.deepak.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User securityUser) {

        User persistedUser = User.builder()
                .username(securityUser.getUsername())
                .password(passwordEncoder.encode(securityUser.getPassword()))
                .email(securityUser.getEmail())
                .description(securityUser.getDescription())
                .role(securityUser.getRole())
                .enabled(securityUser.isEnabled())
                .build();

        if (securityUser.getRole() == null) {
            persistedUser.setRole("ROLE_USER");
        }

        return userRepository.save(persistedUser);
    }

    @Override
    public User updateUser(User updatedUser) {

        User retrivedUser = userRepository.findByUsername(updatedUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user can't exit"));

        // update only the changed field's
        if (updatedUser.getEmail() != "") {
            retrivedUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getRole() != "") {
            retrivedUser.setRole(updatedUser.getRole());
        }

        if (updatedUser.getDescription() != "") {
            retrivedUser.setDescription(updatedUser.getDescription());
        }

        retrivedUser.setEnabled(updatedUser.isEnabled());
        /*
         * retrivedUser.setEmail(updatedUser.getEmail());
         * retrivedUser.setDescription(updatedUser.getDescription());
         * retrivedUser.setEnabled(updatedUser.isEnabled());
         * retrivedUser.setRole(updatedUser.getRole());
         */

        return userRepository.save(retrivedUser);
    }

    @Override
    public void deleteUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user can't exist !"));

        userRepository.delete(user);
    }
}
