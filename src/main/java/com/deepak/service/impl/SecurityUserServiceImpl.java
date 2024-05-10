package com.deepak.service.impl;

import com.deepak.entity.SecurityUser;
import com.deepak.repository.SecurityUserRepository;
import com.deepak.service.SecurityUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .build();

                if(securityUser.getRole()==null){
                    persistedUser.setRole("ROLE_USER");
                }

        return securityUserRepository.save(persistedUser);
    }
}
