package com.deepak.repository;

import com.deepak.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {

    Optional<SecurityUser> findByUsername(String username);

    List<SecurityUser> findByRole(String roles);
}
