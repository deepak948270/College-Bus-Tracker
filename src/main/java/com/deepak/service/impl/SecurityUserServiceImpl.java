package com.deepak.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deepak.entity.SecurityUser;
import com.deepak.repository.SecurityUserRepository;
import com.deepak.service.SecurityUserService;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    private final PasswordEncoder passwordEncoder;

    private final SecurityUserRepository securityUserRepository;

    public SecurityUserServiceImpl(PasswordEncoder passwordEncoder, SecurityUserRepository securityUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.securityUserRepository = securityUserRepository;
    }

    @Override
    public SecurityUser createUser(SecurityUser securityUser) {

        SecurityUser persistedUser = SecurityUser.builder()
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

        return securityUserRepository.save(persistedUser);
    }

    @Override
    public SecurityUser updateUser(SecurityUser updatedUser) {

        SecurityUser retrivedUser = securityUserRepository.findByUsername(updatedUser.getUsername())
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

        return securityUserRepository.save(retrivedUser);
    }

    @Override
    public void deleteUser(String username) {

        SecurityUser user = securityUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user can't exist !"));

        securityUserRepository.delete(user);
    }
}
